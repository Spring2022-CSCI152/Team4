package com.CSCI152.team4.server.Accounts.Interfaces;


import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

import java.util.List;


public interface IAccountRetriever {

    WorkerAccount getAccountInfo(Request request);

    WorkerAccount getOtherAccountInfo(TargetAccountRequest request);

    List<WorkerAccount> getAccounts(Request request);
}
