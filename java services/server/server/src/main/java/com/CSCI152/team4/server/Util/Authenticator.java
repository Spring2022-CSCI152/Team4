package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;

public interface Authenticator {

    //validateToken
    boolean validateToken(String token);
}
