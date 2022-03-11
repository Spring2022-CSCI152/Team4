package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="Admin_Account")
public class AdminAccount extends WorkerAccount {

    @Id
    private String accountId;

    public AdminAccount() {
    }

    public AdminAccount(String email, String password,
                        String firstName, String lastName, String jobTitle, Integer businessId) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.accountId = UUID.randomUUID().toString();
    }

    public AdminAccount(String email, String password,
                        String firstName, String lastName, String jobTitle) {
        super(email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.accountId = UUID.randomUUID().toString();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "accountId='" + accountId + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", timestamp=" + this.getTimestamp() +
                ", jobTitle='" + this.getJobTitle() + '\'' +
                '}';
    }
}
