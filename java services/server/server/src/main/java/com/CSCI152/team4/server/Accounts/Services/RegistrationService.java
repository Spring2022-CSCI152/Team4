package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.*;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Encapsulated all registration activities. Business account
 * registration requires a specialized flow, which is abstracted
 * within this class. Admin and Employee accounts are also registered
 * from within this class. Their default registration methods are
 * registerBusiness, registerAdmin, and registerEmployee
 * each takes their own respective 'CreationRequest'. */

@Service
public class RegistrationService {

    private final AccountsRepoInterface repos;
    private final SecurityManager securityManager;
    private final SettingsRepoInterface settings;

    @Autowired
    public RegistrationService(AccountsRepoInterface repos, SecurityManager securityManager,
                               SettingsRepoInterface settings) {
        this.repos = repos;
        this.securityManager = securityManager;
        this.settings = settings;
    }
    /**
     * Process the Business Registration Request
     * 1. Extract the AdminAccount and encrypt the Password
     * 2. Create the BusinessAccount using the AdminAccount ID
     * 3. Save BusinessAccount to retrieve generated ID
     * 4. Set AdminAccount's businessId and Save
     * 5. Get Token and Clear AdminAccount Password field
     * 6. Create Default Formats for Reports and Profiles
     * 7. Return Account info
     * @param request The request holding the admin account information and business
     *                name
     *
     * @return AdminAccount containing the accounts information and am empty
     * password field*/
    public AdminAccount registerBusiness(BusinessRequestDAO request){
        /*Ensure Fields are Non-null*/
        request.validate();
        //1. Extract the AdminAccount and encrypt the Password
        AdminAccount returnable = request.getAdminAccount();
        encryptPassword(returnable);
        //2. Create the BusinessAccount using the AdminAccount ID
        BusinessAccount newBusinessAccount
                = request.getBusinessAccount(returnable.getAccountIdString());
        //3. Save BusinessAccount to retrieve generated ID
        Integer businessId
                = repos.saveBusinessAccount(newBusinessAccount).getBusinessId();

        //4. Set AdminAccount's businessId and Save
        returnable.setBusinessId(businessId);
        repos.saveAdminAccount(returnable);
        //5. Build Corresponding Report and Profile Settings to defaults
        settings.saveReportFormat(new ReportFormat(businessId));
        settings.saveCustomerProfileFormat(new CustomerProfileFormat(businessId));
        //6. Get Token and Clear AdminAccount Password field
        returnable.setToken(securityManager.generateToken(returnable.getAccountId()));
        returnable.setPassword(null);
        //7. Return Account Info
        return returnable;
    }

    public ResponseEntity<Enum<HttpStatus>> registerAdmin(AdminRequestDAO request){

        /*Ensure Auth and Permissions*/
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_REGISTER);
        /*Ensure Non-null fields*/
        request.validate();
        /*Ensure No Prior Reg*/
        checkForPriorRegistration(request);
        /*Get Business Account*/
        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());
        /*Extract New Admin Account and encrypt password*/
        AdminAccount newAdmin = request.getAdminAccount();
        encryptPassword(newAdmin);
        /*On non-null business account, add admin to admins list*/
        businessAccount.addAdmin(newAdmin.getAccountIdString());
        /*Save updated business account and admin account*/
        repos.saveBusinessAccount(businessAccount);
        repos.saveAdminAccount(newAdmin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    public ResponseEntity<Enum<HttpStatus>> registerEmployee(EmployeeRequestDAO request){

        /*Ensure Auth and Permissions*/
        securityManager.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.ACCOUNTS_REGISTER);

        /*Ensure Non-null fields*/
        request.validate();

        /*Ensure No Prior Reg*/
        checkForPriorRegistration(request);

        /*Get Business Account*/
        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        /*Extract Employee account and encrypt password*/
        EmployeeAccount newEmployee = request.getEmployeeAccount();
        encryptPassword(newEmployee);

        /*On Non null business account, save employee to account*/
        businessAccount.addEmployee(newEmployee.getAccountIdString());

        /*Save updated business account and employee account*/
        repos.saveBusinessAccount(businessAccount);
        repos.saveEmployeeAccount(newEmployee);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void encryptPassword(WorkerAccount account){
        account.setPassword(BCrypt.hashpw(account.getPassword(),
                BCrypt.gensalt(10)));
    }

    private void checkForPriorRegistration(AccountCreationRequestDAO request){
        if(repos.getAccountByEmailAndBusinessId(
                request.getEmail(), request.getBusinessId()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account already registered!");
        }
    }

}

