package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateFromAdminRequest;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@Service
public class AccountManagementService {

    private final AccountsRepoManager repos;
    private final SecurityManager securityManager;

    @Autowired
    public AccountManagementService(AccountsRepoManager repos, SecurityUtil securityManager) {
        this.repos = repos;
        this.securityManager = securityManager;
    }

    /*Get Own Account Info*/
    public WorkerAccount getAccountInfo(Request request){
        securityManager.validateToken(request.getAccountId(), request.getToken());

        if(repos.businessExists(request.getBusinessId())){
            WorkerAccount returnable
                    = repos.getAccountByEmailAndBusinessId(request.getAccountEmail(),
                    request.getBusinessId());
            /*This does not require an Admin, therefore do not call getReturnableOnAdminExists*/
            /*Do not return password!*/
            returnable.setPassword(null);

            return returnable;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account Not Found!");
    }

    /*Get Another Admins Account information: Only Available to Admin Accounts*/
    public AdminAccount getOtherAdminAccountInfo(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        return (AdminAccount) getReturnableOnAdminExists(request.getAccountId(),
                () -> repos.getAccountByEmailAndBusinessId(
                        request.getTargetId().getEmail(), request.getTargetId().getBusinessId()));
    }

    /*Get Another Employees Account information: Only Available to Admin Accounts*/
    public EmployeeAccount getOtherEmployeeAccountInfo(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        return (EmployeeAccount) getReturnableOnAdminExists(request.getAccountId(),
                () -> repos.getAccountByEmailAndBusinessId(
                        request.getTargetId().getEmail(), request.getTargetId().getBusinessId()));
    }

    /*An Admin will Update another Accounts information, but only the
     * mutable fields
     * Account Info is returned as proof
     * of update*/
    public WorkerAccount updateOtherFromAdmin(UpdateFromAdminRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_UPDATE);
        return getReturnableOnAdminExists(request.getAccountId(),
                () -> updateAndSaveOther(request));
    }

    /*Admin updates their own info, Account Info is returned as proof
     * of update*/
    public AdminAccount updateAdminAccount(AdminAccount account){
        securityManager.validateToken(account.getAccountId(), account.getToken());
        return updateAndSaveAdminAccount(account.getAccountId(), account);
    }

    /*Employee Updates their own info, Account Info is returned as proof
    * of update*/
    public EmployeeAccount updateEmployeeAccount(EmployeeAccount account){
        securityManager.validateToken(account.getAccountId(), account.getToken());
        return updateAndSaveEmployeeAccount(account.getAccountId(), account);
    }

    private WorkerAccount updateAndSaveOther(UpdateFromAdminRequest request){

        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        String accountType = businessAccount.getAccountType(request.getTargetId().getAccountIdString());

        /*BECAUSE OF DIFFERENT REPOS, IMPLEMENTATION WAS STUCK TO THIS METHOD, IT VIOLATES OPEN/CLOSE PRINCIPAL*/
        switch(accountType){
            case BusinessAccount.adminAccountType:
                return updateAndSaveAdminAccount(request.getTargetId(),
                        new AdminAccount(request.getBusinessId(), request.getAccountEmail(), request.getPassword(),
                                request.getFirstName(), request.getLastName(), request.getJobTitle())
                );
            case BusinessAccount.employeeAccountType:
                return updateAndSaveEmployeeAccount(request.getTargetId(),
                        new EmployeeAccount(request.getBusinessId(), request.getAccountEmail(), request.getPassword(),
                                request.getFirstName(), request.getLastName(), request.getJobTitle())
                );
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Account Type!");
        }
    }

    private AdminAccount updateAndSaveAdminAccount(AccountId accountIdToUpdate, AdminAccount account){

        AdminAccount accountToSave = repos.getAdminIfExists(accountIdToUpdate);

        updateMutableAccountFields(account, accountToSave);

        AdminAccount returnable = repos.saveAdminAccount(accountToSave);
        /*Do not return password!*/
        returnable.setPassword(null);

        return  returnable;
    }

    private EmployeeAccount updateAndSaveEmployeeAccount(AccountId accountIdToUpdate, EmployeeAccount account){
        EmployeeAccount accountToSave = repos.getEmployeeIfExists(accountIdToUpdate);

        updateMutableAccountFields(account, accountToSave);

        EmployeeAccount returnable = repos.saveEmployeeAccount(accountToSave);
        /*Do not return password!*/
        returnable.setPassword(null);

        return returnable;
    }

    /*Non-mutable fields are Email, AccountId String and BusinessId
    * All other fields are updatable
    *
    * Refer to WorkerAccount for available fields */
    private void updateMutableAccountFields(WorkerAccount newInfo, WorkerAccount originalInfo){

        String fNameToUpdate = newInfo.getFirstName();
        String lNameToUpdate = newInfo.getLastName();
        String jobTitleToUpdate = newInfo.getJobTitle();
        String newPassword = newInfo.getPassword();

        if(fNameToUpdate != null && !fNameToUpdate.isBlank()){
            originalInfo.setFirstName(fNameToUpdate);
        }

        if(lNameToUpdate != null && !lNameToUpdate.isBlank()){
            originalInfo.setLastName(lNameToUpdate);
        }

        if(jobTitleToUpdate != null && !jobTitleToUpdate.isBlank()){
            originalInfo.setJobTitle(jobTitleToUpdate);
        }

        if(newPassword != null && !newPassword.isBlank()){
            String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            originalInfo.setPassword(encryptedPassword);
        }
    }

    /*Update an Employees Permissions from an Admin Account
    * Return the Account permissions as proof of success*/
    public WorkerAccount updateEmployeePermissions(PermissionUpdateRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.PERMISSIONS_EDIT);
        return getReturnableOnAdminExists(request.getAccountId(),
                () -> updatePermissions(request));
    }

    private WorkerAccount updatePermissions(PermissionUpdateRequest request){
        EmployeeAccount accountToUpdate = repos.getEmployeeIfExists(request.getTargetId());
        accountToUpdate.setPermissionsList(request.getPermissions());
        return repos.saveEmployeeAccount(accountToUpdate);
    }

    /*Accepts a Lambda Expression to execute IF the admin is valid AND have matching business ID's*/
    private WorkerAccount getReturnableOnAdminExists(AccountId requestingAccountId, Supplier<WorkerAccount> toReturn){
        if(repos.adminExists(requestingAccountId)){
            WorkerAccount returnable = toReturn.get();
            if(returnable.getBusinessId().equals(requestingAccountId.getBusinessId())){
                returnable.setPassword(null);
                return returnable;
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Business Id!");
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin Does Not Exist!");
    }

    public List<WorkerAccount> getAccounts(Request request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        if(repos.adminExists(request.getAccountId())){
            return repos.getAccountsByBusinessId(request.getBusinessId());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin Does Not Exist!");
    }

    /*Admin can Promote another Account to Admin if not already one*/
    public WorkerAccount promote(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_PROMOTE);

        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());
        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
                .equals(BusinessAccount.employeeAccountType)){

            return getReturnableOnAdminExists(request.getAccountId(), () -> promoteToAdmin(businessAccount, request.getTargetId()));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target is not Employee Account");
    }

    /*Admin can demote another admin to "Employee", this will cause default permissions*/
    public WorkerAccount demote(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_DEMOTE);

        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());
        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
                .equals(BusinessAccount.adminAccountType)){

            return getReturnableOnAdminExists(request.getAccountId(), () -> demoteToEmployee(businessAccount, request.getTargetId()));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target is not Employee Account");
    }

    private WorkerAccount promoteToAdmin(BusinessAccount businessAccount, AccountId accountToPromote){
        businessAccount.promote(accountToPromote.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);

        AdminAccount newAdmin =
                getAdminFromEmployee(repos.getEmployeeIfExists(accountToPromote));

        repos.saveAdminAccount(newAdmin);

        repos.removeEmployeeAccount(accountToPromote);

        if(repos.adminExists(accountToPromote) && !repos.employeeExists(accountToPromote)){
            return repos.getAdminIfExists(accountToPromote);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during promotion!");
    }

    private WorkerAccount demoteToEmployee(BusinessAccount businessAccount, AccountId accountToDemote){
        businessAccount.demote(accountToDemote.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);

        EmployeeAccount newEmployee =
                getEmployeeFromAdmin(repos.getAdminIfExists(accountToDemote));


        repos.saveEmployeeAccount(newEmployee);

        repos.removeAdminAccount(accountToDemote);

        if(!repos.adminExists(accountToDemote) && repos.employeeExists(accountToDemote)){
            return repos.getEmployeeIfExists(accountToDemote);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during demotion!");
    }

    private AdminAccount getAdminFromEmployee(EmployeeAccount account){
        AdminAccount retAccount =
                new AdminAccount(account.getBusinessId(),
                        account.getEmail(), account.getPassword(),
                        account.getFirstName(), account.getLastName(),
                        account.getJobTitle());

        retAccount.setAccountId(account.getAccountId());

        return retAccount;
    }

    private EmployeeAccount getEmployeeFromAdmin(AdminAccount account){
        EmployeeAccount retAccount =
                new EmployeeAccount(account.getBusinessId(),
                        account.getEmail(), account.getPassword(),
                        account.getFirstName(), account.getLastName(),
                        account.getJobTitle());

        retAccount.setAccountId(account.getAccountId());

        return retAccount;
    }
}
