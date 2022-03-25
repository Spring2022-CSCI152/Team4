package com.CSCI152.team4.server.AccountsReformat.AccountClasses;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AccountId implements Serializable {

    private String accountId;
    private String email;
    private Integer businessId;

    public AccountId() {
    }

    public AccountId(String accountId, String email, Integer businessId) {
        this.accountId = accountId;
        this.email = email;
        this.businessId = businessId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
        return getAccountId().equals(accountId1.getAccountId())
                && getEmail().equals(accountId1.getEmail())
                && getBusinessId().equals(accountId1.getBusinessId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId(), getEmail(), getBusinessId());
    }

    @Override
    public String toString() {
        return "AccountId{" +
                "accountId='" + accountId + '\'' +
                ", email='" + email + '\'' +
                ", businessId=" + businessId +
                '}';
    }
}
