package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public class UpdateOtherRequest extends UpdateRequest{

    private AccountId targetId;

    public UpdateOtherRequest(){super();}

    public UpdateOtherRequest(String email, String password, String firstName, String lastName, String jobTitle, AccountId targetId) {
        super(email, password, firstName, lastName, jobTitle);
        this.targetId = targetId;
    }

    public UpdateOtherRequest(String token, AccountId accountId, String email, String password, String firstName, String lastName, String jobTitle, AccountId targetId) {
        super(token, accountId, email, password, firstName, lastName, jobTitle);
        this.targetId = targetId;
    }

    public AccountId getTargetId() {
        return targetId;
    }

    public void setTargetId(AccountId targetId) {
        this.targetId = targetId;
    }
}
