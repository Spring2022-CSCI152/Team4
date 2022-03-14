package com.CSCI152.team4.server.Accounts.Classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountCreationRequest {


    private String token;
    private String requestingAccountId;
    private Integer businessId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String jobTitle;

    public AccountCreationRequest(String token, String requestingAccountId, Integer businessId,
                                       String email, String password,
                                       String firstName, String lastName,
                                       String jobTitle) {
        this.token = token;
        this.requestingAccountId = requestingAccountId;
        this.businessId = businessId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public AccountCreationRequest(){}


    public String getToken() {
        return token;
    }

    public String getRequestingAccountId() {
        return requestingAccountId;
    }

    public void setRequestingAccountId(String requestingAccountId) {
        this.requestingAccountId = requestingAccountId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }


    public void validate() {

        if(token == "" || requestingAccountId == "" || businessId == null && email== "" || password == ""
                || firstName == "" || lastName == "" || jobTitle == ""){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request, Contains Empty Fields");
        }

    }
}
