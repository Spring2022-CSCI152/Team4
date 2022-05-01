package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountPermissionUpdater;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequestDAO;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountPermissionUpdater implements IAccountPermissionUpdater {

    private final AccountsRepoInterface accounts;

    @Autowired
    public AccountPermissionUpdater(AccountsRepoInterface accounts) {
        this.accounts = accounts;
    }

    @Override
    public WorkerAccount updatePermissions(PermissionUpdateRequestDAO request) {
        /*Get Employee Account*/
        EmployeeAccount accountToUpdate = accounts.getEmployeeIfExists(request.getTargetId());
        /*Update Permissions*/
        accountToUpdate.setPermissionsList(request.getPermissions());
        /*Save Changes*/
        accountToUpdate = accounts.saveEmployeeAccount(accountToUpdate);
        /*Nullify Password*/
        accountToUpdate.setPassword(null);
        /*Return account info*/
        return accountToUpdate;
    }
}
