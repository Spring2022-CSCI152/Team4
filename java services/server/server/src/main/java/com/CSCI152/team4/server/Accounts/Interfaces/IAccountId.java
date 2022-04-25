package com.CSCI152.team4.server.Accounts.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public interface IAccountId {

    AccountId getAccountId();

    void setAccountId(String accountId);

    void setAccountId(AccountId accountId);
}
