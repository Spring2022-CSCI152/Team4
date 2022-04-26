package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequest;

public interface IAccountPermissionUpdater {

    WorkerAccount updatePermissions(PermissionUpdateRequest request);
}
