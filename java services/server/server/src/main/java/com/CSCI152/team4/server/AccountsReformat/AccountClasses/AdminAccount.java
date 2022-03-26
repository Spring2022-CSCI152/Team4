package com.CSCI152.team4.server.AccountsReformat.AccountClasses;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Table(name="Admin_Account")
public class AdminAccount extends WorkerAccount{

    @EmbeddedId
    AccountId accountId;

    public AdminAccount() {
        super();
    }

    public AdminAccount(Integer businessId,
                        String email, String password,
                        String firstName, String lastName,
                        String jobTitle) {
            super(businessId, email, password, firstName, lastName, Timestamp.valueOf(now()), jobTitle);
            this.accountId = new AccountId(UUID.randomUUID().toString(), email, businessId);
    }

    public AdminAccount(String email, String password,
                        String firstName, String lastName,
                        String jobTitle) {
        super(email, password, firstName, lastName, Timestamp.valueOf(now()), jobTitle);
        this.accountId = new AccountId(UUID.randomUUID().toString(), email, null);
    }

    public void setAccountId(String accountId){
        this.accountId.setAccountId(accountId);
    }

    public String getAccountId(){
        return this.accountId.getAccountId();
    }

    @Override
    public void setBusinessId(Integer businessId){
        super.setBusinessId(businessId);
        this.accountId.setBusinessId(businessId);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o)
                && this.getAccountId().equals(o);
    }

    @Override
    public String toString() {
        return "AdminAccount{" +
                "accountId=" + accountId.toString() +
                ", email='" + this.getEmail() + '\'' +
                ", businessId='" + this.getBusinessId() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", timestamp=" + this.getTimestamp() +
                ", jobTitle='" + this.getJobTitle() + '\'' +
                '}';
    }
}
