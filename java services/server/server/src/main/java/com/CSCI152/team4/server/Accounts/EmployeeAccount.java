package com.CSCI152.team4.server.Accounts;

import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmployeeAccount extends WorkerAccount{

    @NotEmpty(message = "Permissions can not be empty!")
    private HashMap<String, Boolean> permissions;

    //No permissions passed
    public EmployeeAccount(String email, String password, String firstName, String lastName, String jobTitle) {
        super(email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.buildDefaultPermissions();
    }

    //Permissions Passed
    public EmployeeAccount(String email, String password,
                           String firstName, String lastName,
                           String jobTitle, HashMap<String, Boolean> permissions) {
        super(email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
        this.buildPermissionsFromList(permissions);
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
