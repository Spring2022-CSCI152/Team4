package com.CSCI152.team4.server.Auth.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class AuthRequest extends Request {

    public AuthRequest() {
    }

    public AuthRequest(String token, AccountId accountId) {
        super(token, accountId);
    }
}
