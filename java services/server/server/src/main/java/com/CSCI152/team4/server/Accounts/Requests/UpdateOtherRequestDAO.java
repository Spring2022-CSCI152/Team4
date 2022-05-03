package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public class UpdateOtherRequestDAO extends UpdateRequestDAO {

    private AccountId targetId;

    public UpdateOtherRequestDAO(){super();}

    public UpdateOtherRequestDAO(String email, String password, String firstName, String lastName, String jobTitle, AccountId targetId) {
        super(email, password, firstName, lastName, jobTitle);
        this.targetId = targetId;
    }

    public UpdateOtherRequestDAO(String token, AccountId accountId, String email, String password, String firstName, String lastName, String jobTitle, AccountId targetId) {
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
