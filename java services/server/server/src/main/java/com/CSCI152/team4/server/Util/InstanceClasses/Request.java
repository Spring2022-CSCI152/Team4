package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.Interfaces.RequestInterface;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/*
* This serves as a base class for all requests except the Login Request*/
public class Request implements RequestInterface {

    private String token;

    private AccountId accountId;

    public Request() {
    }

    public Request(String token, AccountId accountId) {
        this.token = token;
        this.accountId = accountId;
    }

    @Override
    public String getAccountIdString() {
        if(accountId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AccountId must not be null!");
        }
        return accountId.getAccountIdString();
    }

    @Override
    public String getAccountEmail() {
        return accountId.getEmail();
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public Integer getBusinessId() {
        return accountId.getBusinessId();
    }

    @Override
    public AccountId getAccountId() {
        return accountId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setAccountId(AccountId accountId) {
        this.accountId = accountId;
    }

    public void setAccountIdString(String accountIdString){
        this.accountId.setAccountIdString(accountIdString);
    }

    public void setBusinessId(Integer businessId){
        this.accountId.setBusinessId(businessId);
    }

    public void setEmail(String password){
        this.accountId.setEmail(password);
    }
}
