package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/*This class acts as an intermediary step between
* Accounts and Admin/Employee Accounts to make
* inheritance issues easier to work with*/
@MappedSuperclass
public class WorkerAccount{

    @NotBlank
    private Integer businessId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp timestamp;
    private String jobTitle;

    public WorkerAccount(){}


    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public WorkerAccount(Integer businessId, String email, String password,
                         String firstName, String lastName,
                         Timestamp timestamp,
                         String jobTitle) {
        this.businessId = businessId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
        this.jobTitle = jobTitle;
    }
    public WorkerAccount(String email, String password,
                         String firstName, String lastName,
                         Timestamp timestamp,
                         String jobTitle) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
        this.jobTitle = jobTitle;
    }

    public Timestamp getTimestamp() {
        return timestamp;
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

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkerAccount that = (WorkerAccount) o;
        return this.email.equals(that.email)
                && this.password.equals(that.password)
                && Objects.equals(this.firstName, that.firstName)
                && Objects.equals(this.lastName, that.lastName)
                && Objects.equals(this.timestamp, that.timestamp)
                && Objects.equals(this.jobTitle, that.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, firstName, lastName, timestamp, jobTitle);
    }

    @Override
    public String toString() {
        return "WorkerAccount{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", timestamp=" + timestamp +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
