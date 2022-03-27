package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.Permissions;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Entity
@Table(name="Employee_Account")
public class EmployeeAccount extends WorkerAccount{

    @EmbeddedId
    AccountId accountId;

    @Column(nullable = false)
    @ElementCollection
    private List<String> permissionsList;

    public EmployeeAccount() {
    }

    public EmployeeAccount(Integer businessId,
                           String email, String password,
                           String firstName, String lastName,
                           String jobTitle, List<String> permissionsList) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(now()), jobTitle);
        this.accountId = new AccountId(UUID.randomUUID().toString(), email, businessId);
        setPermissionsList(permissionsList);
    }

    public EmployeeAccount(Integer businessId,
                           String email, String password,
                           String firstName, String lastName,
                           String jobTitle) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(now()), jobTitle);
        this.accountId = new AccountId(UUID.randomUUID().toString(), email, businessId);
        permissionsList = new ArrayList<>();
    }


    /**
     * Set the permissions of the Employee account. Guarantees to ONLY
     * set valid permissions to prevent security issues.
     * */
    public void setPermissionsList(List<String> grantedPermissions){
        this.permissionsList = new ArrayList<>();
        if(grantedPermissions == null) return; //cleared list == no permissions

        for(Permissions p : Permissions.values()){
            if(grantedPermissions.contains(p.toString())){
                this.permissionsList.add(p.toString());
            }
        }
    }

    public List<String> getPermissionsList(){
        return this.permissionsList;
    }

    public void setAccountId(String accountId){
        this.accountId.setAccountId(accountId);
    }

    public AccountId getAccountId() { return this.accountId; }

    @Override
    public String getAccountIdString(){
        return this.accountId.getAccountId();
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
                ", permissionsList='" + this.permissionsList.toString() + '\'' +
                '}';
    }
}
