package com.CSCI152.team4.server.Accounts.Classes;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public class EmployeeAccountCreationRequest extends AccountCreationRequest{

    private List<String> permissions;

    public EmployeeAccountCreationRequest(String token, String requestingAccountId, Integer businessId,
                                       String email, String password,
                                       String firstName, String lastName,
                                       String jobTitle, List<String> permissions) {
        super(token, requestingAccountId, businessId, email, password, firstName, lastName, jobTitle);
        this.permissions = permissions;
    }

    public EmployeeAccountCreationRequest(){}

    public EmployeeAccount getEmployeeAccount(){

        return new EmployeeAccount(this.getEmail(), this.getPassword(),
                this.getFirstName(), this.getLastName(),
                this.getJobTitle(), this.getPermissions(), this.getBusinessId());
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    @Override
    public void validate() {

        super.validate();
        if(this.permissions == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request, Contains Empty Fields");
        }

    }
}
