package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Accounts.Requests.LoginRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SessionService {

    private final SecurityManager securityManager;
    private final AccountsRepoManager accounts;

    @Autowired
    public SessionService(SecurityUtil securityManager, AccountsRepoManager accounts) {
        this.securityManager = securityManager;
        this.accounts = accounts;
    }

    public WorkerAccount login(LoginRequest request){

        WorkerAccount account =
                accounts.getAccountByEmailAndBusinessId(
                        request.getEmail(), request.getBusinessId());

        validatePassword(request.getPassword(), account.getPassword());

        validateBusinessRelation(request.getBusinessId(), account.getAccountIdString());

        account.setToken(securityManager.generateToken(new AccountId(account.getAccountIdString(), account.getEmail(), account.getBusinessId())));

        /*Do not return Password!*/
        account.setPassword(null);

        return account;
    }

    private void validatePassword(String plainText, String hashed){
        if(!BCrypt.checkpw(plainText, hashed)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void validateBusinessRelation(Integer businessId, String accountId){

        BusinessAccount businessAccount = accounts.getBusinessIfExists(businessId);

        try{
            String accountType = businessAccount.getAccountType(accountId);
            boolean isValidated =
                    (accountType.equals(BusinessAccount.adminAccountType)
                            && businessAccount.getAdmins().contains(accountId))
                    || (accountType.equals(BusinessAccount.employeeAccountType)
                            && businessAccount.getEmployees().contains(accountId));

            if(!isValidated){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Business ID");
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Not Found");
        }

    }

    public ResponseEntity<Enum<HttpStatus>> logout(Request request){

        securityManager.invalidateToken(request.getAccountId(), request.getToken());

        return new ResponseEntity<Enum<HttpStatus>>(HttpStatus.OK);
    }
}
