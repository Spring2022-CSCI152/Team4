package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;

public class BusinessRequest extends AccountCreationRequest {

    private String businessName;

    public BusinessRequest(){}

    public BusinessRequest(String businessName) {
        this.businessName = businessName;
    }

    public BusinessRequest(String email, String password,
                           String firstName, String lastName,
                           String jobTitle, String businessName) {
        super(email, password, firstName, lastName, jobTitle);
        this.businessName = businessName;
    }

    public BusinessAccount getBusinessAccount(String adminId){
        return new BusinessAccount(businessName, adminId);
    }

    public AdminAccount getAdminAccount(){
        return new AdminAccount(getBusinessId(),
                getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }
}
