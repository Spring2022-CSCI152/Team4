package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

import java.util.List;
import java.util.Objects;

public class PermissionUpdateRequest extends Request {


    AccountId accountToUpdateId;
    List<String> permissions;

    public PermissionUpdateRequest() {
    }

    public PermissionUpdateRequest(AccountId accountToUpdateId, List<String> permissions) {
        this.accountToUpdateId = accountToUpdateId;
        this.permissions = permissions;
    }

    public PermissionUpdateRequest(String token, AccountId accountId, AccountId accountToUpdateId, List<String> permissions) {
        super(token, accountId);
        this.accountToUpdateId = accountToUpdateId;
        this.permissions = permissions;
    }

    public AccountId getAccountToUpdateId() {
        return accountToUpdateId;
    }

    public void setAccountToUpdateId(AccountId accountToUpdateId) {
        this.accountToUpdateId = accountToUpdateId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PermissionUpdateRequest)) return false;
        PermissionUpdateRequest that = (PermissionUpdateRequest) o;
        return super.getAccountId().equals(that.getAccountId()) && getAccountToUpdateId().equals(that.getAccountToUpdateId()) && Objects.equals(getPermissions(), that.getPermissions()) && getToken().equals(that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getAccountId(), getAccountToUpdateId(), getPermissions(), getToken());
    }
}
