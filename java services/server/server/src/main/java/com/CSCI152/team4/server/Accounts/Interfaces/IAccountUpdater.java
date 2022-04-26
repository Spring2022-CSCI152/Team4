package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequest;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequest;

public interface IAccountUpdater {

    WorkerAccount updateOther(UpdateOtherRequest request);

    WorkerAccount updateSelf(UpdateRequest request);
}
