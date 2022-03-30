package com.CSCI152.team4.server.Auth.Requests;

public class AuthRequest {

    private String token;
    private String accountId;

    public AuthRequest() {
    }

    public AuthRequest(String token, String accountId) {
        this.token = token;
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
