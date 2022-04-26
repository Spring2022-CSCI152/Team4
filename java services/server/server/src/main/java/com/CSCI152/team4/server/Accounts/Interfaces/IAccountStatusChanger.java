package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;

public interface IAccountStatusChanger {

    WorkerAccount promote(TargetAccountRequest request);

    WorkerAccount demote(TargetAccountRequest request);

}
