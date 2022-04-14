package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

public class UpdateFromAdminRequest extends TargetAccountRequest{

    private String email;
    private String firstName;
    private String lastName;
    private String jobTitle;

    public UpdateFromAdminRequest() {
    }

    public UpdateFromAdminRequest(String token,
                                  AccountId accountId, AccountId targetId,
                                  String email,
                                  String firstName, String lastName,
                                  String jobTitle) {
        super(token, accountId, targetId);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
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
}
