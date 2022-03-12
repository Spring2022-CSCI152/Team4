package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountsService {


    private final AdminAccountRepo adminRepo;
    private final EmployeeAccountRepo employeeAccountRepo;
    private final BusinessAccountRepo businessAccountRepo;
    private final AccountAuthenticator accountAuthenticator;

    @Autowired
    public AccountsService(AdminAccountRepo adminRepo, EmployeeAccountRepo employeeAccountRepo, BusinessAccountRepo businessAccountRepo) {
        this.adminRepo = adminRepo;
        this.employeeAccountRepo = employeeAccountRepo;
        this.businessAccountRepo = businessAccountRepo;
        this.accountAuthenticator = new AccountAuthenticator();
    }

    public WorkerAccount registerBusinessAccount(BusinessRegistrationRequest request){
        //TODO: Add Password hashing, email verification
        AdminAccount returnableAccount = this.buildAdminAccountFromRequest(request);
        BusinessAccount tempBusinessAcc = this.buildBusinessAcctFromAdminAndName(returnableAccount.getAccountId(), request.getBusinessName());

        this.saveBusinessAndAdminAccounts(tempBusinessAcc, returnableAccount);
        this.clearPassword(returnableAccount);

        return returnableAccount;

    }

    private AdminAccount buildAdminAccountFromRequest(BusinessRegistrationRequest request){
        //TODO: Add Verification for all fields submitted
        return new AdminAccount(request.getEmail(), request.getPassword(), request.getFirstName(), request.getLastName(), request.getJobTitle());
    }

    private BusinessAccount buildBusinessAcctFromAdminAndName(String adminId, String businessName){
        //TODO: Add checks for business Name
        BusinessAccount returnableAccount = new BusinessAccount(businessName);
        returnableAccount.addAdmin(adminId);

        return returnableAccount;
    }

    private void saveBusinessAndAdminAccounts(BusinessAccount businessAccount, AdminAccount adminAccount){

        if(businessAccount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        /*
        * Business ID constraints for BusinessAccounts cause this two step process.
        * The Business ID is generated upon saving the businessAccount. The returned
        * ID is then used to set the adminAccount's business ID before saving.*/
        Integer generatedBusinessId =
                saveBusinessAccountAndReturnId(businessAccount);

        //extracted for clarity. This line, and the above line can all be done in the same line
        adminAccount.setBusinessId(generatedBusinessId);
        adminRepo.save(adminAccount);
    }

    private void clearPassword(WorkerAccount account){
        if(account != null) account.setPassword("");
    }

    private Integer saveBusinessAccountAndReturnId(BusinessAccount businessAccount){
        Integer businessId = businessAccountRepo.save(businessAccount).getBusinessId();
        return businessId;
    }

    //Admin Account Creation
    /*To create an all new accounts under a Business Account MUST be created by
    * another admin account. Therefore, a token from the Client Side will
    * be passed with all the information needed to create an admin account.
    *
    * As of V 0.0.1 this token will simply be the Account ID of the
    * requesting Admin Account*/
    public String createAdminAccount(AdminAccountCreationRequest request){
        //TODO: Throw Error if cannot validate
        accountAuthenticator.validateToken(request.getToken());

        //validate all fields of request are filled
        request.validate();

        if(adminRepo.existsById(request.getRequestingAccountId())){

            //Validation
            throwErrorIfInvalidBusinessIdForAdmin(request.getRequestingAccountId(), request.getBusinessId());

            createAndSaveAdminAccount(request.getAdminAccount());

            return "Account Creation Success";
        }

        return "Account Creation Failed";

    }

    private void throwErrorIfInvalidBusinessIdForAdmin(String adminAccountId, Integer businessId) throws ResponseStatusException
    {
        if(businessAccountRepo.existsById(businessId)){
            List<String> adminsInBusiness = businessAccountRepo.findById(businessId).get().getAdmins();

            if(!adminsInBusiness.contains(adminAccountId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account is not registered with this business");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business Account not found");
        }
    }

    private void createAndSaveAdminAccount(AdminAccount newAccount){
        saveAdminAccountToBusiness(newAccount.getAccountId(), newAccount.getBusinessId());
        adminRepo.save(newAccount);
    }

    private void saveAdminAccountToBusiness(String accountId, Integer businessId){
        if(businessAccountRepo.existsById(businessId)){
            BusinessAccount businessAccount = businessAccountRepo.findById(businessId).get();
            businessAccount.addAdmin(accountId);

            businessAccountRepo.save(businessAccount);
        }
    }

    //Employee Account Creation
    public String createEmployeeAccount(EmployeeAccountCreationRequest request){
        //TODO: Throw Error if cannot validate
        accountAuthenticator.validateToken(request.getToken());

        //validate all fields of request are filled
        request.validate();

        if(employeeAccountRepo.existsById(request.getRequestingAccountId())){

            //Validation
            throwErrorIfInvalidBusinessIdForEmployee(request.getRequestingAccountId(), request.getBusinessId());

            createAndSaveEmployeeAccount(request.getEmployeeAccount());

            return "Account Creation Success";
        }

        return "Account Creation Failed";

    }
    private void throwErrorIfInvalidBusinessIdForEmployee(String employeeAccountId, Integer businessId) throws ResponseStatusException
    {
        if(businessAccountRepo.existsById(businessId)){
            List<String> employeesInBusiness = businessAccountRepo.findById(businessId).get().getEmployees();

            if(!employeesInBusiness.contains(employeeAccountId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account is not registered with this business");
            }
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Business Account not found");
        }
    }

    private void createAndSaveEmployeeAccount(EmployeeAccount newAccount){
        saveEmployeeAccountToBusiness(newAccount.getAccountId(), newAccount.getBusinessId());
        employeeAccountRepo.save(newAccount);
    }

    private void saveEmployeeAccountToBusiness(String accountId, Integer businessId){
        if(businessAccountRepo.existsById(businessId)){
            BusinessAccount businessAccount = businessAccountRepo.findById(businessId).get();
            businessAccount.addEmployee(accountId);

            businessAccountRepo.save(businessAccount);
        }
    }


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
