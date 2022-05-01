package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.*;
import com.CSCI152.team4.server.Accounts.Requests.*;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequestDAO;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequestDAO;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class AccountManagementService {

    private final SecurityManager securityManager;
    private final IAccountRetriever accounts;
    private final IAccountUpdater updater;
    private final IAccountPermissionUpdater permissions;
    private final IAccountStatusChanger status;

    @Autowired
    public AccountManagementService(SecurityManager securityManager,
                                    IAccountRetriever accounts, IAccountUpdater updater,
                                    IAccountPermissionUpdater permissions, IAccountStatusChanger status) {
        this.securityManager = securityManager;
        this.accounts = accounts;
        this.updater = updater;
        this.permissions = permissions;
        this.status = status;
    }

    public WorkerAccount getAccountInfo(Request request){
        securityManager.validateToken(request.getAccountId(), request.getToken());
        return accounts.getAccountInfo(request);
    }

    public WorkerAccount getOtherAccountInfo(TargetAccountRequestDAO request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return accounts.getOtherAccountInfo(request);
    }

    public List<WorkerAccount> getAccounts(Request request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        return accounts.getAccounts(request);
    }

    public WorkerAccount updateInfo(UpdateRequestDAO request){
        securityManager.validateToken(request.getAccountId(), request.getToken());
        return updater.updateSelf(request);
    }

    public WorkerAccount updateOther(UpdateOtherRequestDAO request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_UPDATE);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return updater.updateOther(request);
    }

    public WorkerAccount updateEmployeePermissions(PermissionUpdateRequestDAO request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.PERMISSIONS_EDIT);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        if(request.getAccountId().equals(request.getTargetId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CANNOT UPDATE OWN PERMISSIONS!");
        }
        return permissions.updatePermissions(request);
    }

    public WorkerAccount promote(TargetAccountRequestDAO request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_PROMOTE);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return status.promote(request);
    }

    public WorkerAccount demote(TargetAccountRequestDAO request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_DEMOTE);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return status.demote(request);
    }

    private void checkForSameBusinessIds(AccountId requester, AccountId target){
        if(!requester.getBusinessId().equals(target.getBusinessId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Different Business IDs");
        }
    }
}
