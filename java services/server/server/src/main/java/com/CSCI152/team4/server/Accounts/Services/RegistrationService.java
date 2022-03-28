package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Repos.RepoManager;
import com.CSCI152.team4.server.Accounts.Requests.AccountCreationRequest;
import com.CSCI152.team4.server.Accounts.Requests.AdminRequest;
import com.CSCI152.team4.server.Accounts.Requests.BusinessRequest;
import com.CSCI152.team4.server.Accounts.Requests.EmployeeRequest;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
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

    private final RepoManager repos;
    private final AccountAuthenticator authenticator;

    @Autowired
    public RegistrationService(RepoManager repos, AccountAuthenticator authenticator) {
        this.repos = repos;
        this.authenticator = authenticator;
    }

    /**
     * Process the Business Registration Request
     * 1. Extract the AdminAccount and encrypt the Password
     * 2. Create the BusinessAccount using the AdminAccount ID
     * 3. Save BusinessAccount to retrieve generated ID
     * 4. Set AdminAccount's businessId and Save
     * 5. Get Token and Clear AdminAccount Password field
     * 6. Return Account info
     * @param request The request holding the admin account information and business
     *                name
     *
     * @return AdminAccount containing the accounts information and am empty
     * password field*/
    public AdminAccount registerBusiness(BusinessRequest request){
        /*
        * Forgive me, I allowed this function to do several things.
        * To break this function up, a large series of functions
        * and confusions ensue, and I haven't the skill at
        * present to make it clean.
        *
        * As a token of my apology, see the comments below
        * */

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

        //5. Get Token and Clear AdminAccount Password field
        returnable.setToken(authenticator.getToken(returnable.getAccountIdString()));
        returnable.setPassword("");

        //6. Return Account Info
        return returnable;
    }

    public ResponseEntity<Enum<HttpStatus>> registerAdmin(AdminRequest request){

        validateRequest(request);
        checkForPriorRegistration(request);

        BusinessAccount businessAccount = getBusinessAccountIfValid(request);

        AdminAccount newAdmin = request.getAdminAccount();
        encryptPassword(newAdmin);

        assert businessAccount != null;
        businessAccount.addAdmin(newAdmin.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);
        repos.saveAdminAccount(newAdmin);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    public ResponseEntity<Enum<HttpStatus>> registerEmployee(EmployeeRequest request){

        validateRequest(request);
        checkForPriorRegistration(request);

        BusinessAccount businessAccount = getBusinessAccountIfValid(request);

        EmployeeAccount newEmployee = request.getEmployeeAccount();
        encryptPassword(newEmployee);

        assert businessAccount != null;
        businessAccount.addEmployee(newEmployee.getAccountIdString());

        repos.saveBusinessAccount(businessAccount);
        repos.saveEmployeeAccount(newEmployee);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public void encryptPassword(WorkerAccount account){
        account.setPassword(BCrypt.hashpw(account.getPassword(),
                BCrypt.gensalt(10)));
    }

    private void validateRequest(AccountCreationRequest request){
        authenticator.validateToken(request.getToken(), request.getRequestingAccountId());
        request.validate();
    }

    private void checkForPriorRegistration(AccountCreationRequest request){
        if(repos.getAccountByEmailAndBusinessId(
                request.getEmail(), request.getBusinessId()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private BusinessAccount getBusinessAccountIfValid(AccountCreationRequest request){

        BusinessAccount returnable = repos.getBusinessIfExists(request.getBusinessId());

        try{
            if(returnable.getAccountType(request.getRequestingAccountId())
                    .equals(BusinessAccount.adminAccountType)){
                return returnable;
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return null;
    }
}

