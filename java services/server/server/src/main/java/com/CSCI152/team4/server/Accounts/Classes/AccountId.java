package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountId implements Serializable {


    @NotBlank
    private String accountIdString;
    @Column(insertable = false, updatable = false)
    private String email;
    @Column(insertable = false, updatable = false)
    private Integer businessId;

    public AccountId() {
    }

    public AccountId(String accountIdString, String email, Integer businessId) {
        this.accountIdString = accountIdString;
        this.email = email;
        this.businessId = businessId;
    }

    public String getAccountIdString() {
        return accountIdString;
    }

    public void setAccountIdString(String accountIdString) {
        this.accountIdString = accountIdString;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountId)) return false;
        AccountId accountId1 = (AccountId) o;
        return getAccountIdString().equals(accountId1.getAccountIdString())
                && getEmail().equals(accountId1.getEmail())
                && getBusinessId().equals(accountId1.getBusinessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountIdString(), getEmail(), getBusinessId());
    }

    @Override
    public String toString() {
        return "AccountId{" +
                "accountId='" + accountIdString + '\'' +
                ", email='" + email + '\'' +
                ", businessId=" + businessId +
                '}';
    }
}
