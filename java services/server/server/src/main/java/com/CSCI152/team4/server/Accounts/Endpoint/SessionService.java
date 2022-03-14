package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.springframework.security.crypto.bcrypt.BCrypt.checkpw;

@Service
public class SessionService {

    private final AdminAccountRepo adminAccountRepo;
    private final EmployeeAccountRepo employeeAccountRepo;
    private final BusinessAccountRepo businessAccountRepo;
    private final AccountAuthenticator accountAuthenticator;

    @Autowired
    public SessionService(AdminAccountRepo adminRepo, EmployeeAccountRepo employeeAccountRepo,
                          BusinessAccountRepo businessAccountRepo, AccountAuthenticator accountAuthenticator) {
        this.adminAccountRepo = adminRepo;
        this.employeeAccountRepo = employeeAccountRepo;
        this.businessAccountRepo = businessAccountRepo;
        this.accountAuthenticator = accountAuthenticator;
    }


    public WorkerAccount userLogin(WorkerAccount account){
        //If valid email and password
        String plainTextPW = account.getPassword();
        String accountId = setAccountInfoAndReturnId(account);
        if(verifyLoginCredentials(plainTextPW, account.getPassword()) &&
                verifyBusinessConnection(account.getBusinessId(), accountId)){

            //Then set token, clear password
            account.setToken(accountAuthenticator.getToken(accountId));
            account.setPassword("");
            //return account info containing token
            return account;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean verifyLoginCredentials(String plainText, String hashed){
        return isValidPassword(plainText, hashed);
    }

    private String setAccountInfoAndReturnId(WorkerAccount account){

        Optional<AdminAccount> adminAccount =
                adminAccountRepo.findByEmailAndBusinessId(account.getEmail(), account.getBusinessId());
        Optional<EmployeeAccount> employeeAccount =
                employeeAccountRepo.findByEmailAndBusinessId(account.getEmail(), account.getBusinessId());
        String returnStr = "";
        if(adminAccount.isPresent()){
            AdminAccount persisted = adminAccount.get();
            returnStr = persisted.getAccountId();
            account.setPassword(persisted.getPassword());

        }
        else if(employeeAccount.isPresent()){
            EmployeeAccount persisted = employeeAccount.get();
            returnStr = persisted.getAccountId();
            account.setPassword(persisted.getPassword());
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return returnStr;
    }

    private boolean isValidPassword(String plainText, String hashedPassword){
        return BCrypt.checkpw(plainText, hashedPassword);
    }

    private boolean verifyBusinessConnection(Integer businessId, String accountId){

        Optional<BusinessAccount> optional = businessAccountRepo.findById(businessId);

        if(optional.isPresent()){
            BusinessAccount businessAccount = optional.get();
            return businessAccount.getAdmins().contains(accountId)
                    || businessAccount.getEmployees().contains(accountId);
        }

        return false;


    }
    public void userLogout(WorkerAccount account){
        accountAuthenticator.invalidateToken(account.getToken());
    }

}
