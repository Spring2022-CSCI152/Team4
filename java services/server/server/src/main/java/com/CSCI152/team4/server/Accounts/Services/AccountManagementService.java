package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Repos.RepoManager;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    private final RepoManager repos;
    private final AccountAuthenticator authenticator;

    @Autowired
    public AccountManagementService(RepoManager repos, AccountAuthenticator authenticator) {
        this.repos = repos;
        this.authenticator = authenticator;
    }

    public AdminAccount updateAdminAccount(AdminAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());

        if(repos.adminExists(account.getAccountId())){
            return updateAndSaveAdminAccount(account);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private AdminAccount updateAndSaveAdminAccount(AdminAccount account){

        AdminAccount accountToSave = repos.getAdminIfExists(account.getAccountId());

        updateMutableAccountFields(account, accountToSave);

        repos.saveAdminAccount(accountToSave);

        return  getReturnableAdmin(account.getAccountId());
    }

    public ResponseEntity<Enum<HttpStatus>> updateEmployeeAccountFromAdmin(AdminAccount account, EmployeeAccount update){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());

        if(repos.adminExists(account.getAccountId())){
            updateAndSaveEmployeeAccount(update);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public EmployeeAccount updateEmployeeAccount(EmployeeAccount account){
        authenticator.validateToken(account.getToken(), account.getAccountIdString());

        if(repos.employeeExists(account.getAccountId())){
            return updateAndSaveEmployeeAccount(account);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
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

        if(!fNameToUpdate.isBlank()){
            originalInfo.setFirstName(fNameToUpdate);
        }

        if(!lNameToUpdate.isBlank()){
            originalInfo.setLastName(lNameToUpdate);
        }

        if(!jobTitleToUpdate.isBlank()){
            originalInfo.setJobTitle(jobTitleToUpdate);
        }
    }

    public AdminAccount getAdminAccountInfo(String token, AccountId accountId){
        authenticator.validateToken(token, accountId.getAccountId());

        if(repos.adminExists(accountId)){
            return getReturnableAdmin(accountId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public EmployeeAccount getEmployeeAccountInfo(String token, AccountId accountId){
        authenticator.validateToken(token, accountId.getAccountId());

        if(repos.employeeExists(accountId)){
            return getReturnableEmployee(accountId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public AdminAccount getAdminAccountFromAdmin(String token, AccountId requestingAccount, AccountId targetAccount){
        authenticator.validateToken(token, requestingAccount.getAccountId());

        if(repos.adminExists(requestingAccount)){
            return getReturnableAdmin(targetAccount);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    public EmployeeAccount getEmployeeAccountFromAdmin(String token, AccountId requestingAccount, AccountId targetAccount){
        authenticator.validateToken(token, requestingAccount.getAccountId());

        if(repos.adminExists(requestingAccount)){
            return getReturnableEmployee(targetAccount);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private AdminAccount getReturnableAdmin(AccountId accountId){
        AdminAccount returnable = repos.getAdminIfExists(accountId);
        if (returnable != null) returnable.setPassword("");
        return returnable;
    }

    private EmployeeAccount getReturnableEmployee(AccountId accountId){
        EmployeeAccount returnable = repos.getEmployeeIfExists(accountId);
        if (returnable != null) returnable.setPassword("");
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

}
