package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;

public interface Authenticator {


    //isAdmin
    boolean isAdminAccount(WorkerAccount account) throws ClassNotFoundException;

    //isEmployee
    boolean isEmployeeAccount(WorkerAccount account) throws ClassNotFoundException;

    //validateToken
    boolean validateToken(WorkerAccount account, String token);
}
