package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class AccountsService {


    private final AdminAccountRepo adminRepo;
    private final EmployeeAccountRepo employeeAccountRepo;
    private final BusinessAccountRepo businessAccountRepo;

    @Autowired
    public AccountsService(AdminAccountRepo adminRepo, EmployeeAccountRepo employeeAccountRepo, BusinessAccountRepo businessAccountRepo) {
        this.adminRepo = adminRepo;
        this.employeeAccountRepo = employeeAccountRepo;
        this.businessAccountRepo = businessAccountRepo;
    }

    //BusinessAccount Creation
    public void createBusinessAccount(){}

    //Admin Account Creation
    public void createAdminAccount(){}


    //Employee Account Creation
    public void createEmployeeAccount(){}


    //Save Report Format Information
    public void saveReportFormat(){}


    //Account Deletion
    public void deleteAccount(){}


    //Account Info Retrieval
    public void getAccountInfo(){}


    //Login
    public void userLogin(){}


    //Logout
    public void userLogout(){}



    //Set Permissions
    public void setPermissions(){}


    //Retrieve Permissions
    public void getPermissions(){}


    //Edit Account Info
    public void updateAccountInfo(){}

    //Test

}
