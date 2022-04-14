package com.CSCI152.team4.server.test;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class EmbeddedAccountsRequest extends Request {

    private AccountId targetId;

    public EmbeddedAccountsRequest() {
    }

    public EmbeddedAccountsRequest(String token, AccountId accountId, AccountId targetId) {
        super(token, accountId);
        this.targetId = targetId;
    }

    public AccountId getTargetId() {
        return targetId;
    }

    public void setTargetId(AccountId targetId) {
        this.targetId = targetId;
    }

    @Override
    public String toString() {
        return "EmbeddedAccountsRequest{\n" +
                "requestingId=" + super.getAccountId().toString() +
                "\ntargetId=" + targetId +
                "\n}";
    }
}
