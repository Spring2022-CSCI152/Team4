package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;

public class EmployeeRequestDAO extends AccountCreationRequestDAO {

    public EmployeeRequestDAO() {
    }

    public EmployeeRequestDAO(String email, String password,
                              String firstName, String lastName,
                              String jobTitle) {
        super(email, password, firstName, lastName, jobTitle);
    }

    public EmployeeRequestDAO(String token, AccountId accountId,
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
