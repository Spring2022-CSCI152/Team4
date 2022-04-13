package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public interface RequestInterface {

    public String getAccountIdString();

    public String getAccountEmail();

    public String getToken();

    public Integer getBusinessId();

    public AccountId getAccountId();
}
