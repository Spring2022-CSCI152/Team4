package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class TargetAccountRequest extends Request {

    private AccountId targetId;

    public  TargetAccountRequest(){
        super();
    }
    public TargetAccountRequest(AccountId targetId) {
        this.targetId = targetId;
    }

    public TargetAccountRequest(String token, AccountId accountId, AccountId targetId) {
        super(token, accountId);
        this.targetId = targetId;
    }

    public AccountId getTargetId() {
        return targetId;
    }

    public void setTargetId(AccountId targetId) {
        this.targetId = targetId;
    }
}
