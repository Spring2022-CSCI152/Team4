package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public interface TargetedRequest {

    AccountId getTargetId();

    void setTargetId(AccountId targetId);

    AccountId getAccountId();

    void setAccountId();
}
