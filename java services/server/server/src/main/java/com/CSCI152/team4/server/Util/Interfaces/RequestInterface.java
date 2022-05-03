package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public interface RequestInterface {

    String getAccountIdString();

    String getAccountEmail();

    String getToken();

    Integer getBusinessId();

    AccountId getAccountId();
}
