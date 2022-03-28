package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        return new AdminAccount(getEmail(), getPassword(),
                getFirstName(), getLastName(),
                getJobTitle());
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public void validate(){

        if( getEmail().isBlank() || getPassword().isBlank()
                || getFirstName().isBlank() || getLastName().isBlank()
                || getJobTitle().isBlank() || businessName.isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Request, Contains Empty Fields");
        }
    }
}
