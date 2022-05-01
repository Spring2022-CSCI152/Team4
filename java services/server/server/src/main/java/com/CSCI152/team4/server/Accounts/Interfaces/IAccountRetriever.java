package com.CSCI152.team4.server.Accounts.Interfaces;


import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequestDAO;
import com.CSCI152.team4.server.Util.InstanceClasses.RequestDAO;

import java.util.List;


public interface IAccountRetriever {

    WorkerAccount getAccountInfo(RequestDAO requestDAO);

    WorkerAccount getOtherAccountInfo(TargetAccountRequestDAO request);

    List<WorkerAccount> getAccounts(RequestDAO requestDAO);
}
