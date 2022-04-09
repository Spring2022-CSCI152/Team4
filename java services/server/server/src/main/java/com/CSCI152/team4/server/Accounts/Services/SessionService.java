package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Accounts.Requests.LoginRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SessionService {

    private final TokenAuthenticator authenticator;
    private final AccountsRepoManager repos;

    @Autowired
    public SessionService(TokenAuthenticator authenticator, AccountsRepoManager repos) {
        this.authenticator = authenticator;
        this.repos = repos;
    }

    public WorkerAccount login(LoginRequest request){

        WorkerAccount account =
                repos.getAccountByEmailAndBusinessId(
                        request.getEmail(), request.getBusinessId());

        validatePassword(request.getPassword(), account.getPassword());

        validateBusinessRelation(request.getBusinessId(), account.getAccountIdString());

        account.setToken(authenticator.getToken(account.getAccountIdString()));

        return account;
    }

    private void validatePassword(String plainText, String hashed){
        if(!BCrypt.checkpw(plainText, hashed)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void validateBusinessRelation(Integer businessId, String accountId){

        BusinessAccount businessAccount = repos.getBusinessIfExists(businessId);

        try{
            String accountType = businessAccount.getAccountType(accountId);
            boolean isValidated =
                    (accountType.equals(BusinessAccount.adminAccountType)
                            && businessAccount.getAdmins().contains(accountId))
                    || (accountType.equals(BusinessAccount.employeeAccountType)
                            && businessAccount.getEmployees().contains(accountId));

            if(!isValidated){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Enum<HttpStatus>> logout(String token, AccountId accountId){

        authenticator.invalidateToken(token, accountId.getAccountIdString());

        return new ResponseEntity<Enum<HttpStatus>>(HttpStatus.OK);
    }
}
