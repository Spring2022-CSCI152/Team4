package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.Permissions;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@Table(name="Employee_Account")
public class EmployeeAccount extends WorkerAccount {


    @Id
    private String accountId;

    @Transient
    private HashMap<String, Boolean> permissions;

    @Column(nullable = false)
    @ElementCollection
    private List<String> permissions_list;

    public EmployeeAccount() {
    }

    //No permissions passed
    public EmployeeAccount(String email, String password, String firstName, String lastName,
                           String jobTitle, Integer businessId) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.buildDefaultPermissions();
        this.permissions_list = this.getPermissions();
    }

    //Permissions Passed
    public EmployeeAccount(String email, String password,
                           String firstName, String lastName,
                           String jobTitle, HashMap<String, Boolean> permissions, int businessId) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.buildPermissionsFromList(permissions);
        this.permissions_list = this.getPermissions();
    }

    //Inherently Distrust the Permissions Map, and iterate over
    //enum values and only pull those vals from the passed in map
    private void buildPermissionsFromList(HashMap<String, Boolean> permissions) {
        for(Permissions permission : Permissions.values()){
            if(!this.permissions.containsKey(permission.toString())) {
                this.permissions.put(permission.toString(),
                                     permissions.get(permission.toString()));
            }
        }
    }

    //By Default, All permissions are turned off
    private void buildDefaultPermissions() {

        if(this.permissions == null){
            this.permissions = new HashMap<String, Boolean>();
        }
        for(Permissions permission : Permissions.values()){
            if(!this.permissions.containsKey(permission.toString())) {
                this.permissions.put(permission.toString(), Boolean.valueOf(false));
            }
        }
    }

    //Not called, but needed for Persistence
    private void setPermissions(HashMap<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    //Return a List containing the keys of permissions
    //Design Decision to reduce data movement and allows
    //easier portability with other modules and updates
    public List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        for(String key : this.permissions.keySet()){
            if((Boolean) this.permissions.get(key))
                permissions.add(key);
        }
        return permissions;
    }
}
