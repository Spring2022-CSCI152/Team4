package com.CSCI152.team4.server.Accounts.Requests;

public class LoginRequest {

    String email;
    String password;
    Integer businessId;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password, Integer businessId) {
        this.email = email;
        this.password = password;
        this.businessId = businessId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
