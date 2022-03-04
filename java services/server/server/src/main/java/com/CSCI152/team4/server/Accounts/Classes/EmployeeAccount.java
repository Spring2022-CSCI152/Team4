package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Employee_Account")
public class EmployeeAccount extends WorkerAccount {


    @Id
    private String accountId;

    @Column(nullable = false)
    @ElementCollection
    private List<String> permissions_list;

    public EmployeeAccount() {
    }

    //No permissions passed
    public EmployeeAccount(String email, String password, String firstName, String lastName,
                           String jobTitle, Integer businessId) {
        super(businessId,
                email,
                password,
                firstName,
                lastName,
                Timestamp.valueOf(LocalDateTime.now()),
                jobTitle);
        this.accountId = UUID.randomUUID().toString();
        this.permissions_list = new ArrayList<>();
    }

    //Permissions Passed
    public EmployeeAccount(String email, String password,
                           String firstName, String lastName,
                           String jobTitle, List<String> permissions, int businessId) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.accountId = UUID.randomUUID().toString();
        this.setPermissions_list(permissions);
    }


    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "EmployeeAccount{" +
                "accountId='" + accountId + '\'' +
                ", permissions_list=" + permissions_list +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", firstName='" + this.getFirstName() + '\'' +
                ", lastName='" + this.getLastName() + '\'' +
                ", timestamp=" + this.getTimestamp() +
                ", jobTitle='" + this.getJobTitle()+ '\'' +
                '}';
    }

    public List<String> getPermissions_list() {
        return permissions_list;
    }

    public void setPermissions_list(List<String> permissions_list) {

        //clear array list
        this.permissions_list = new ArrayList<>();
        if(permissions_list == null) return; //cleared list == no permissions
        //refill
        for(Permissions p : Permissions.values()){
            if(permissions_list.contains(p.toString())){
                this.permissions_list.add(p.toString());
            }
        }
    }

    public String getAccountId() {
        return this.accountId;
    }
}
