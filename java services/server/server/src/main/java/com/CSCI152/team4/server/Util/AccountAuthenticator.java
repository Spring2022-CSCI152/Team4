package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class AccountAuthenticator implements Authenticator {


    public AccountAuthenticator(){}

    @Override
    public boolean validateToken(String token, String requestAccountId) {
        /*TODO: Implement a call to the Auth endpoint to authenticate
        *  account*/
        //For now, just return true
        return true;
    }

    @Override
    public String getToken(String accountId) {
        /*TODO: Implement a Call to Auth endpoint to create a token
        *  for now just return accountId*/
        return accountId;
    }

    @Override
    public void invalidateToken(String token) {
        //TODO: Make call to Auth endpoint to invalidate token
    }


}
