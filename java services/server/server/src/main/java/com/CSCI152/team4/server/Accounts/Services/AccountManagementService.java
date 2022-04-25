package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountRetriever;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountUpdater;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequest;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Utils.AccountRetriever;
import com.CSCI152.team4.server.Accounts.Utils.AccountUpdater;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Supplier;

import static org.hibernate.internal.util.ReflectHelper.getConstructor;

@Service
public class AccountManagementService {

    private final AccountsRepoManager repos;
    private final SecurityManager securityManager;
    private final IAccountRetriever accounts;
    private final IAccountUpdater updater;

    /*
    * Responsibilities:
    * AccountRetrieval -> AccountRetriever
    * AccountUpdates -> AccountUpdater
    * Promote/Demote -> AccountStatusChanger
    * Account Transpose -> AccountClassTranspose
    * */
    @Autowired
    public AccountManagementService(AccountsRepoManager repos, SecurityUtil securityManager, AccountRetriever accounts, AccountUpdater updater) {
        this.repos = repos;
        this.securityManager = securityManager;
        this.accounts = accounts;
        this.updater = updater;
    }

    /*Get Own Account Info*/
    public WorkerAccount getAccountInfo(Request request){
        securityManager.validateToken(request.getAccountId(), request.getToken());
        return accounts.getAccountInfo(request);
    }

    public WorkerAccount getOtherAccountInfo(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        if(!request.getAccountId().getBusinessId().equals(request.getTargetId().getBusinessId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Different Business IDs");
        }
        return accounts.getOtherAccountInfo(request);
    }

    public List<WorkerAccount> getAccounts(Request request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        return accounts.getAccounts(request);
    }

    public WorkerAccount updateInfo(UpdateRequest request){
        securityManager.validateToken(request.getAccountId(), request.getToken());
        return updater.updateSelf(request);
    }

    public WorkerAccount updateOther(UpdateOtherRequest request){
        securityManager.validateToken(request.getAccountId(), request.getToken());
        return updater.updateOther(request);
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
        assert businessAccount != null;

        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
                .equals(BusinessAccount.adminAccountType)){

            return getReturnableOnAdminExists(request.getAccountId(), () -> demoteToEmployee(businessAccount, request.getTargetId()));
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target is not Employee Account");
    }

    private WorkerAccount promoteToAdmin(BusinessAccount businessAccount, AccountId accountToPromote){
        businessAccount.promote(accountToPromote.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);

        try{
            /* Transpose EmployeeAccount to AdminAccount*/
            AdminAccount newAdmin = (AdminAccount) transposeTo(AdminAccount.class, repos.getEmployeeIfExists(accountToPromote));

            /*Save to AdminTable*/
            repos.saveAdminAccount(newAdmin);

            /*Remove from EmployeeTable*/
            repos.removeEmployeeAccount(accountToPromote);

            /* Return database successfully swapped entries*/
            if(repos.adminExists(accountToPromote) && !repos.employeeExists(accountToPromote)){
                return repos.getAdminIfExists(accountToPromote);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during promotion!");
    }

    private WorkerAccount demoteToEmployee(BusinessAccount businessAccount, AccountId accountToDemote){
        businessAccount.demote(accountToDemote.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);

        try{
            /*Transpose AdminAccount to EmployeeAccount*/
            EmployeeAccount newEmployee = (EmployeeAccount) transposeTo(EmployeeAccount.class, repos.getAdminIfExists(accountToDemote));

            /*Save to Employee Table*/
            repos.saveEmployeeAccount(newEmployee);

            /*Delete From Admin Table*/
            repos.removeAdminAccount(accountToDemote);

            /* Return database successfully swapped entries*/
            if(!repos.adminExists(accountToDemote) && repos.employeeExists(accountToDemote)){
                return repos.getEmployeeIfExists(newEmployee.getAccountId());
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during demotion!");
    }

    private WorkerAccount transposeTo(Class accountType, WorkerAccount account)
            throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor con = null;

        /*Iterate throw class constructors*/
        for(Constructor c : accountType.getDeclaredConstructors()){
            if(c.getParameterCount() == 6){
                con = c;
            }
        }
        /*Create new instance using 6 param constructor*/
        WorkerAccount ret =  (WorkerAccount) con.newInstance(account.getBusinessId(),
                account.getEmail(), account.getPassword(),
                account.getFirstName(), account.getLastName(),
                account.getJobTitle());

        /*Set account id to original ID*/
        ret.setAccountId(account.getAccountIdString());
        return ret;
    }
}
