package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class TargetAccountRequestDAO extends Request {

    private AccountId targetId;

    public TargetAccountRequestDAO(){
        super();
    }
    public TargetAccountRequestDAO(AccountId targetId) {
        this.targetId = targetId;
    }

    public TargetAccountRequestDAO(String token, AccountId accountId, AccountId targetId) {
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
