package com.CSCI152.team4.server.AccountsReformat.Requests;

import com.CSCI152.team4.server.AccountsReformat.AccountClasses.AdminAccount;

public class AdminRequest extends AccountCreationRequest{

    public AdminRequest() {
    }

    public AdminRequest(String token,
                        String requestingAccountId, Integer businessId,
                        String email, String password,
                        String firstName, String lastName,
                        String jobTitle) {

        super(token, requestingAccountId, businessId, email, password, firstName, lastName, jobTitle);
    }

    public AdminAccount getAdminAccount(){
        return new AdminAccount(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }
}
