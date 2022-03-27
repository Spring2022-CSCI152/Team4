package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

import java.util.List;
import java.util.Objects;

public class PermissionUpdateRequest {

    AccountId requestingAccountId;
    AccountId accountToUpdateId;
    List<String> permissions;
    String token;

    public PermissionUpdateRequest() {
    }

    public PermissionUpdateRequest(String requestingAccountId, String requestingAccountEmail, Integer businessId,
                                   String accountToUpdateId, String accountToUpdateEmail, List<String> permissions,
                                   String token){

        this.requestingAccountId = new AccountId(requestingAccountId, requestingAccountEmail, businessId);
        this.accountToUpdateId = new AccountId(accountToUpdateId, accountToUpdateEmail, businessId);
        this.permissions = permissions;
        this.token = token;

    }
    public PermissionUpdateRequest(AccountId requestingAccountId, AccountId accountToUpdate, List<String> permissions,
                                   String token) {
        this.requestingAccountId = requestingAccountId;
        this.accountToUpdateId = accountToUpdate;
        this.permissions = permissions;
        this.token = token;
    }

    public AccountId getRequestingAccountId() {
        return requestingAccountId;
    }

    public void setRequestingAccountId(String requestingAccountId, String requestingAccountEmail, Integer businessId){
        this.requestingAccountId = new AccountId(requestingAccountId, requestingAccountEmail, businessId);
    }

    public void setRequestingAccountId(AccountId requestingAccountId) {
        this.requestingAccountId = requestingAccountId;
    }

    public AccountId getAccountToUpdateId() {
        return accountToUpdateId;
    }

    public void setAccountToUpdateId(String accountToUpdateId, String accountToUpdateEmail, Integer businessId){
        this.accountToUpdateId = new AccountId(accountToUpdateId, accountToUpdateEmail, businessId);
    }

    public void setAccountToUpdateId(AccountId accountToUpdate) {
        this.accountToUpdateId = accountToUpdate;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestAccountIdString(){
        return requestingAccountId.getAccountId();
    }

    public String getAccountToUpdateIdString(){
        return accountToUpdateId.getAccountId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionUpdateRequest)) return false;
        PermissionUpdateRequest that = (PermissionUpdateRequest) o;
        return getRequestingAccountId().equals(that.getRequestingAccountId()) && getAccountToUpdateId().equals(that.getAccountToUpdateId()) && Objects.equals(getPermissions(), that.getPermissions()) && getToken().equals(that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestingAccountId(), getAccountToUpdateId(), getPermissions(), getToken());
    }
}
