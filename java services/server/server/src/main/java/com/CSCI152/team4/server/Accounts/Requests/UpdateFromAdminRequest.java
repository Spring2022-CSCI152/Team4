package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public class UpdateFromAdminRequest extends TargetAccountRequest{

    private String firstName;
    private String lastName;
    private String jobTitle;
    private String password;

    public UpdateFromAdminRequest() {
    }

    public UpdateFromAdminRequest(String token,
                                  AccountId accountId, AccountId targetId,
                                  String password,
                                  String firstName, String lastName,
                                  String jobTitle) {
        super(token, accountId, targetId);
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
