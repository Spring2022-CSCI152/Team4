package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.*;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequest;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Utils.AccountPermissionUpdater;
import com.CSCI152.team4.server.Accounts.Utils.AccountRetriever;
import com.CSCI152.team4.server.Accounts.Utils.AccountStatusChanger;
import com.CSCI152.team4.server.Accounts.Utils.AccountUpdater;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
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

    public WorkerAccount getOtherAccountInfo(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_VIEW);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
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
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_UPDATE);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return updater.updateOther(request);
    }

    public WorkerAccount updateEmployeePermissions(PermissionUpdateRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.PERMISSIONS_EDIT);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        if(request.getAccountId().equals(request.getTargetId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "CANNOT UPDATE OWN PERMISSIONS!");
        }
        return permissions.updatePermissions(request);
    }

    public WorkerAccount promote(TargetAccountRequest request){
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_PROMOTE);
        checkForSameBusinessIds(request.getAccountId(), request.getTargetId());
        return status.promote(request);
    }

    public WorkerAccount demote(TargetAccountRequest request){
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
