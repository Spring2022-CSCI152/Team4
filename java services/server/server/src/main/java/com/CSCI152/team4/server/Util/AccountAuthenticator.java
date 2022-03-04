package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;

public class AccountAuthenticator implements Authenticator {



    @Override
    public boolean isAdminAccount(WorkerAccount account) throws ClassNotFoundException {
        return Class.forName("com.CSCI152.team4.server.Accounts.Classes.AdminAccount")
                .isInstance(account);
    }

    @Override
    public boolean isEmployeeAccount(WorkerAccount account) throws ClassNotFoundException {
        return Class.forName("com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount")
                .isInstance(account);
    }

    @Override
    public boolean validateToken(WorkerAccount account, String token) {
        /*TODO: Implement a call to the Auth endpoint to authenticate
        *  account*/
        //For now, just return true
        return true;
    }
}
