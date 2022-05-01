package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;

import java.util.List;
import java.util.Objects;

public class PermissionUpdateRequestDAO extends TargetAccountRequestDAO {


    List<String> permissions;

    public PermissionUpdateRequestDAO() {
        super();
    }

    public PermissionUpdateRequestDAO(List<String> permissions) {
        super();
        this.permissions = permissions;
    }


    public PermissionUpdateRequestDAO(String token, AccountId accountId, AccountId targetId, List<String> permissions) {
        super(token, accountId, targetId);
        this.permissions = permissions;
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
        if (!(o instanceof PermissionUpdateRequestDAO)) return false;
        PermissionUpdateRequestDAO that = (PermissionUpdateRequestDAO) o;
        return super.getAccountId().equals(that.getAccountId())
                && super.getTargetId().equals(that.getTargetId())
                && Objects.equals(getPermissions(), that.getPermissions())
                && getToken().equals(that.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getAccountId(), getTargetId(), getPermissions(), getToken());
    }
}
