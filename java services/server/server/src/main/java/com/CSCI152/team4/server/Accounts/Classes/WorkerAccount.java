package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

/*This class acts as an intermediary step between
* Accounts and Admin/Employee Accounts to make
* inheritance issues easier to work with*/
public class WorkerAccount extends Account {

    //accountId will be a UUID cast to String for DB compatibility
    @Id
    private String accountId;


    private int businessId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Timestamp timestamp;
    private String jobTitle;

    public WorkerAccount(int businessId, String email, String password,
                         String firstName, String lastName,
                         Timestamp timestamp,
                         String jobTitle) {
        this.businessId = businessId;
        this.accountId = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
        this.jobTitle = jobTitle;
    }

    public String getAccountId() {
        return accountId;
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
        return this.accountId.equals(that.accountId)
                && this.email.equals(that.email)
                && this.password.equals(that.password)
                && Objects.equals(this.firstName, that.firstName)
                && Objects.equals(this.lastName, that.lastName)
                && Objects.equals(this.timestamp, that.timestamp)
                && Objects.equals(this.jobTitle, that.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, email, password, firstName, lastName, timestamp, jobTitle);
    }

    @Override
    public String toString() {
        return "WorkerAccount{" +
                "accountId='" + accountId + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", timestamp=" + timestamp +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
