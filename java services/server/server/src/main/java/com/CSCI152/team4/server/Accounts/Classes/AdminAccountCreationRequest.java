package com.CSCI152.team4.server.Accounts.Classes;

public class AdminAccountCreationRequest extends AccountCreationRequest {



    public AdminAccountCreationRequest(String token, String requestingAccountId, Integer businessId,
                                       String email, String password,
                                       String firstName, String lastName,
                                       String jobTitle) {
        super(token, requestingAccountId, businessId, email, password, firstName, lastName, jobTitle);
    }

    public AdminAccountCreationRequest(){}

    public AdminAccount getAdminAccount(){
        return new AdminAccount(this.getEmail(), this.getPassword(),
                                this.getFirstName(), this.getLastName(),
                                this.getJobTitle(), this.getBusinessId());
    }


}
