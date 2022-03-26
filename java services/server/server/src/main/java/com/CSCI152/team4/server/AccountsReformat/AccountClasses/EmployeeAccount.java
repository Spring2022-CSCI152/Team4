package com.CSCI152.team4.server.AccountsReformat.AccountClasses;

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
        setPermissions(permissionsList);
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
    private void setPermissions(List<String> grantedPermissions){
        this.permissionsList = new ArrayList<>();
        if(grantedPermissions == null) return; //cleared list == no permissions

        for(Permissions p : Permissions.values()){
            if(grantedPermissions.contains(p.toString())){
                this.permissionsList.add(p.toString());
            }
        }
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
                ", permissionsList='" + this.permissionsList.toString() + '\'' +
                '}';
    }
}
