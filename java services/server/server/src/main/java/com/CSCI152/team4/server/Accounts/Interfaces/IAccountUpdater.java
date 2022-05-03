package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequestDAO;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequestDAO;

public interface IAccountUpdater {

    WorkerAccount updateOther(UpdateOtherRequestDAO request);

    WorkerAccount updateSelf(UpdateRequestDAO request);
}
