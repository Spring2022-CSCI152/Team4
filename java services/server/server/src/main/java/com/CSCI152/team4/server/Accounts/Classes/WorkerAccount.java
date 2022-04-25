package com.CSCI152.team4.server.Accounts.Classes;


import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * This class serves as the base for AdminAccounts and EmployeeAccounts.
 * It will be a mapped superclass to allow compatibility with Hibernate and
 * Data JPA*/

@MappedSuperclass
public abstract class WorkerAccount {
//No real reason for it to be abstract, but it implies that other classes will extend this

    @Column(nullable = false, insertable = false, updatable = false)
    private Integer businessId;
    @Column(nullable = false, insertable = false, updatable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;

    private Timestamp timestamp;
    private String jobTitle;

    @Transient
    private String token;

    public WorkerAccount(Integer businessId,
                         String email, String password,
                         String firstName, String lastName,
                         Timestamp timestamp, String jobTitle) {
        this.businessId = businessId;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
        this.jobTitle = jobTitle;
    }

    public WorkerAccount(String email,
                         String password, String firstName,
                         String lastName, Timestamp timestamp,
                         String jobTitle) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timestamp = timestamp;
        this.jobTitle = jobTitle;
    }

    public WorkerAccount() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token){
        this.token = token;
    };

    public abstract String getAccountIdString();

    abstract AccountId getAccountId();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkerAccount)) return false;
        WorkerAccount that = (WorkerAccount) o;
        return getBusinessId().equals(that.getBusinessId())
                && getEmail().equals(that.getEmail())
                && getPassword().equals(that.getPassword())
                && getFirstName().equals(that.getFirstName())
                && getLastName().equals(that.getLastName())
                && getJobTitle().equals(that.getJobTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }

}
