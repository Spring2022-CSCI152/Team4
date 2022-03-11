package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessRegistrationRequest;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
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

    public WorkerAccount registerBusinessAccount(BusinessRegistrationRequest request){
        //TODO: Add Password hashing
        AdminAccount returnableAccount = this.buildAdminAccountFromRequest(request);
        BusinessAccount tempBusinessAcc = this.buildBusinessAcctFromAdminAndName(returnableAccount.getAccountId(), request.getBusinessName());

        this.saveBusinessAndAdminAccounts(tempBusinessAcc, returnableAccount);
        this.clearPassword(returnableAccount);

        return returnableAccount;

    }

    private AdminAccount buildAdminAccountFromRequest(BusinessRegistrationRequest request){
        return new AdminAccount(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getJobTitle());
    }

    private BusinessAccount buildBusinessAcctFromAdminAndName(String adminId, String businessName){
        BusinessAccount returnableAccount = new BusinessAccount(businessName);
        returnableAccount.addAdmin(adminId);

        return returnableAccount;
    }

    private void saveBusinessAndAdminAccounts(BusinessAccount businessAccount, AdminAccount adminAccount){
        Integer generatedBusinessId =
                businessAccountRepo.save(businessAccount).getBusinessId();

        if(generatedBusinessId == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        adminAccount.setBusinessId(generatedBusinessId);
        adminRepo.save(adminAccount);
    }

    private void clearPassword(WorkerAccount account){
        if(account != null) account.setPassword("");
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

}
