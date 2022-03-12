package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import org.springframework.context.annotation.Bean;

public class AccountAuthenticator implements Authenticator {

    public AccountAuthenticator(){}

    @Override
    public boolean validateToken(String token) {
        /*TODO: Implement a call to the Auth endpoint to authenticate
        *  account*/
        //For now, just return true
        return true;
    }
}
