package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;

public class EmployeeRequest extends AccountCreationRequest{

    public EmployeeRequest() {
    }

    public EmployeeRequest(String token,
                           String requestingAccountId, Integer businessId,
                           String email, String password,
                           String firstName, String lastName,
                           String jobTitle) {
        super(token, requestingAccountId, businessId,
                email, password, firstName, lastName, jobTitle);
    }

    public EmployeeAccount getEmployeeAccount(){
        return new EmployeeAccount(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }
}
