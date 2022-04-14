package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateFromAdminRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
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
    private final TokenAuthenticator authenticator;

    @Autowired
    public AccountManagementService(AccountsRepoManager repos, TokenAuthenticator authenticator) {
        this.repos = repos;
        this.authenticator = authenticator;
    }

    /*Get Own Account Info*/
    public WorkerAccount getAccountInfo(Request request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        if(repos.businessExists(request.getBusinessId())){
            WorkerAccount returnable
                    = repos.getAccountByEmailAndBusinessId(request.getAccountEmail(),
                    request.getBusinessId());
            /*Do not return password!*/
            returnable.setPassword(null);
            return returnable;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business Account Not Found!");
    }

    /*Get Another Admins Account information: Only Available to Admin Accounts*/
    public AdminAccount getOtherAdminAccountInfo(TargetAccountRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        return (AdminAccount) getReturnableOnAdminExists(request.getAccountId(),
                () -> repos.getAccountByEmailAndBusinessId(
                        request.getTargetId().getEmail(), request.getTargetId().getBusinessId()));
    }

    /*Get Another Employees Account information: Only Available to Admin Accounts*/
    public EmployeeAccount getOtherEmployeeAccountInfo(TargetAccountRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        return (EmployeeAccount) getReturnableOnAdminExists(request.getAccountId(),
                () -> repos.getAccountByEmailAndBusinessId(
                        request.getTargetId().getEmail(), request.getTargetId().getBusinessId()));
    }

    /*An Admin will Update another Accounts information, but only the
     * mutable fields
     * Account Info is returned as proof
     * of update*/
    public WorkerAccount updateOtherFromAdmin(UpdateFromAdminRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        return getReturnableOnAdminExists(request.getAccountId(),
                () -> updateAndSaveOther(request));
    }

    /*Admin updates their own info, Account Info is returned as proof
     * of update*/
    public AdminAccount updateAdminAccount(AdminAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());
        return updateAndSaveAdminAccount(account.getAccountId(), account);
    }

    /*Employee Updates their own info, Account Info is returned as proof
    * of update*/
    public EmployeeAccount updateEmployeeAccount(EmployeeAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());
        return updateAndSaveEmployeeAccount(account.getAccountId(), account);
    }

    private WorkerAccount updateAndSaveOther(UpdateFromAdminRequest request){

        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        String accountType = businessAccount.getAccountType(request.getTargetId().getAccountIdString());
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
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
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
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        if(repos.adminExists(request.getAccountId())){
            return repos.getAccountsByBusinessId(request.getBusinessId());
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin Does Not Exist!");
    }

    /*
    * TODO: PROMOTE AND DEMOTE
    * */
}
