package com.CSCI152.team4.server.Accounts.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AccountCreationRequest extends Request {


    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String jobTitle;

    public AccountCreationRequest(){}

    public AccountCreationRequest(String email, String password,
                                  String firstName, String lastName,
                                  String jobTitle) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public AccountCreationRequest(String token, AccountId accountId,
                                  String email, String password,
                                  String firstName, String lastName,
                                  String jobTitle) {
        super(token, accountId);
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobTitle = jobTitle;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void validate() {

        if(super.getToken().equals("") || super.getAccountIdString().equals("")
                || super.getBusinessId() == null
                && email.equals("") || password.equals("")
                || firstName.equals("") || lastName.equals("")
                || jobTitle.equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid Request, Contains Empty Fields");
        }

    }
}
