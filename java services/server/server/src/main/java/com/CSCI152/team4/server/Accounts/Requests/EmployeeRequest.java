package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;

public class EmployeeRequest extends AccountCreationRequest{

    public EmployeeRequest() {
    }

    public EmployeeRequest(String email, String password,
                           String firstName, String lastName,
                           String jobTitle) {
        super(email, password, firstName, lastName, jobTitle);
    }

    public EmployeeRequest(String token, AccountId accountId,
                           String email, String password,
                           String firstName, String lastName,
                           String jobTitle) {
        super(token, accountId, email, password, firstName, lastName, jobTitle);
    }

    public EmployeeAccount getEmployeeAccount(){
        return new EmployeeAccount(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }
}
