package com.CSCI152.team4.server.Accounts.Classes;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class BusinessRegistrationRequest {

    private String businessName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp timestamp;
    private String jobTitle;

    public BusinessRegistrationRequest(String businessName, String email, String password, String firstName, String lastName, String jobTitle) {
        this.businessName = businessName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
        this.jobTitle = jobTitle;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String toString() {
        return "BusinessRegistrationRequest{" +
                "businessName='" + businessName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", timestamp=" + timestamp +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
