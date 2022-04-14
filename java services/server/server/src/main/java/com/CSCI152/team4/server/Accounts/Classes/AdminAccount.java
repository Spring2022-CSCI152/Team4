package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Table(name="Admin_Account")
public class AdminAccount extends WorkerAccount{

    @Column(nullable = false)
    @EmbeddedId
    AccountId accountId = new AccountId();

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
        this.accountId.setAccountIdString(accountId);
        this.accountId.setEmail(getEmail());
        this.accountId.setBusinessId(getBusinessId());
    }

    public void setAccountId(AccountId accountId)
    {
        this.accountId = accountId;
    }

    @Override
    public AccountId getAccountId() { return this.accountId; }

    @Override
    public String getAccountIdString(){
        return this.accountId.getAccountIdString();
    }

    @Override
    public void setBusinessId(Integer businessId){
        if(getBusinessId() == null){
            super.setBusinessId(businessId);
            this.accountId.setBusinessId(businessId);
        }
    }

    @Override
    public void setEmail(String email){
        if(getEmail() == null){
            super.setEmail(email);
            this.accountId.setEmail(email);
        }
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o)
                && this.getAccountIdString().equals(o);
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
