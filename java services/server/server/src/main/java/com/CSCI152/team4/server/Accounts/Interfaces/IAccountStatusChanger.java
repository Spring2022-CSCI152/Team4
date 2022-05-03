package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequestDAO;

public interface IAccountStatusChanger {

    WorkerAccount promote(TargetAccountRequestDAO request);

    WorkerAccount demote(TargetAccountRequestDAO request);

}
