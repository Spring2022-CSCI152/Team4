package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Util.AccountsRepoManager;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.function.Supplier;

@Service
public class AccountManagementService {

    /**
     * This Class Serves as a Getter/Setter Service
     * for All account types.
     *
     * Post Requests will be handled by directly mapping
     * the Supplied Account information to the database
     * entities
     *
     * The exception to this rule is the 'Permissions'
     * for an employee account. To change permissions,
     * a PermissionChangeRequest will be used to ensure
     * ONLY admins can change the permissions.
     *
     */

    private final AccountsRepoManager repos;
    private final TokenManager authenticator;

    @Autowired
    public AccountManagementService(AccountsRepoManager repos, TokenManager authenticator) {
        this.repos = repos;
        this.authenticator = authenticator;
    }

    public AdminAccount updateAdminAccount(AdminAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());

        return (AdminAccount) getReturnableIfAdminExists(account.getAccountId(),
                () -> updateAndSaveAdminAccount(account));
    }

    private AdminAccount updateAndSaveAdminAccount(AdminAccount account){

        AdminAccount accountToSave = repos.getAdminIfExists(account.getAccountId());

        updateMutableAccountFields(account, accountToSave);

        repos.saveAdminAccount(accountToSave);

        return  getReturnableAdmin(account.getAccountId());
    }

    public ResponseEntity<Enum<HttpStatus>> updateEmployeeAccountFromAdmin(String token, AccountId adminAccountId, EmployeeAccount update){
        authenticator.validateToken(token, adminAccountId.getAccountIdString());

        if(repos.adminExists(adminAccountId)){
            updateAndSaveEmployeeAccount(update);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public EmployeeAccount updateEmployeeAccount(EmployeeAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());

        return (EmployeeAccount) getReturnableIfEmployeeExists(account.getAccountId(),
                () -> updateAndSaveEmployeeAccount(account));

    }

    private EmployeeAccount updateAndSaveEmployeeAccount(EmployeeAccount account){
        EmployeeAccount accountToSave = repos.getEmployeeIfExists(account.getAccountId());

        updateMutableAccountFields(account, accountToSave);

        repos.saveEmployeeAccount(accountToSave);

        return getReturnableEmployee(account.getAccountId());
    }

    private void updateMutableAccountFields(WorkerAccount newInfo, WorkerAccount originalInfo){

        String fNameToUpdate = newInfo.getFirstName();
        String lNameToUpdate = newInfo.getLastName();
        String jobTitleToUpdate = newInfo.getJobTitle();
        String newPassword = newInfo.getPassword();

        if(!fNameToUpdate.isBlank()){
            originalInfo.setFirstName(fNameToUpdate);
        }

        if(!lNameToUpdate.isBlank()){
            originalInfo.setLastName(lNameToUpdate);
        }

        if(!jobTitleToUpdate.isBlank()){
            originalInfo.setJobTitle(jobTitleToUpdate);
        }

        if(!newPassword.isBlank()){
            String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            originalInfo.setPassword(encryptedPassword);
        }
    }

    public WorkerAccount getAccountInfo(String token, AccountId account){

        if(repos.businessExists(account.getBusinessId())){
            BusinessAccount businessAccount = repos.getBusinessIfExists(account.getBusinessId());

            System.out.println(account.getAccountIdString());
            if(businessAccount.getAccountType(account.getAccountIdString())
                    .equals(BusinessAccount.adminAccountType)){
                return getAdminAccountInfo(token, account);
            } else if(businessAccount.getAccountType(account.getAccountIdString())
                    .equals(BusinessAccount.employeeAccountType)) {
                return getEmployeeAccountInfo(token, account);
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

    }

    private AdminAccount getAdminAccountInfo(String token, AccountId accountId){
        authenticator.validateToken(token, accountId.getAccountIdString());

        return (AdminAccount) getReturnableIfAdminExists(accountId,
                () -> getReturnableAdmin(accountId));
    }

    private EmployeeAccount getEmployeeAccountInfo(String token, AccountId accountId){
        authenticator.validateToken(token, accountId.getAccountIdString());

        return (EmployeeAccount) getReturnableIfEmployeeExists(accountId,
                () -> getReturnableEmployee(accountId));
    }

    public AdminAccount getAdminAccountFromAdmin(String token, AccountId requestingAccount, AccountId targetAccount){
        authenticator.validateToken(token, requestingAccount.getAccountIdString());
        return (AdminAccount) getReturnableIfAdminExists(requestingAccount,
                () -> getReturnableAdmin(targetAccount));
    }

    public EmployeeAccount getEmployeeAccountFromAdmin(String token, AccountId requestingAccount, AccountId targetAccount){
        authenticator.validateToken(token, requestingAccount.getAccountIdString());

        return (EmployeeAccount) getReturnableIfAdminExists(requestingAccount,
                () -> getReturnableEmployee(targetAccount));
    }

    private AdminAccount getReturnableAdmin(AccountId accountId){
        AdminAccount returnable = repos.getAdminIfExists(accountId);
        if (returnable != null) returnable.setPassword("");
        return returnable;
    }

    private EmployeeAccount getReturnableEmployee(AccountId accountId){
        EmployeeAccount returnable = repos.getEmployeeIfExists(accountId);
        if (returnable != null) returnable.setPassword(null);
        return returnable;
    }

    public ResponseEntity<Enum<HttpStatus>> updateEmployeePermissions(PermissionUpdateRequest request){
        authenticator.validateToken(request.getToken(), request.getRequestAccountIdString());

        if(repos.adminExists(request.getRequestingAccountId())){
            setEmployeePermissionsAndSave(request.getAccountToUpdateId(), request.getPermissions());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    private void setEmployeePermissionsAndSave(AccountId accountId, List<String> permissions){
        EmployeeAccount accountToUpdate = repos.getEmployeeIfExists(accountId);
        accountToUpdate.setPermissionsList(permissions);
        repos.saveEmployeeAccount(accountToUpdate);
    }

    private WorkerAccount getReturnableIfAdminExists(AccountId adminAccountId, Supplier<WorkerAccount> toReturn){
        if(repos.adminExists(adminAccountId)){
            return toReturn.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private WorkerAccount getReturnableIfEmployeeExists(AccountId employeeAccountId, Supplier<WorkerAccount> toReturn){
        if(repos.employeeExists(employeeAccountId)){
            return toReturn.get();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
