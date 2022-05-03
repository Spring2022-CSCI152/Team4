package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;

public class AdminRequest extends AccountCreationRequest{

    public AdminRequest() {
    }

    public AdminRequest(String email, String password,
                        String firstName, String lastName,
                        String jobTitle) {
        super(email, password, firstName, lastName, jobTitle);
    }

    public AdminRequest(String token, AccountId accountId,
                        String email, String password,
                        String firstName, String lastName,
                        String jobTitle) {
        super(token, accountId, email, password, firstName, lastName, jobTitle);
    }


    public AdminAccount getAdminAccount(){
        return new AdminAccount(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }
}
