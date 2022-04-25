package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.Authenticator;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SecurityUtil implements SecurityManager {

    private Authenticator authenticator;
    private AccountsRepoInterface accounts;

    @Autowired
    public SecurityUtil(TokenAuthenticator authenticator, AccountsRepoInterface accounts) {
        this.authenticator = authenticator;
        this.accounts = accounts;
    }

    @Override
    public void validateToken(AccountId accountId, String token) throws ResponseStatusException {
        authenticator.validateToken(accountId.getAccountIdString(), token);
    }

    @Override
    public void invalidateToken(AccountId accountId, String token) throws ResponseStatusException {
        authenticator.invalidateToken(accountId.getAccountIdString(), token);
    }

    @Override
    public void refreshToken(AccountId accountId, String token) throws ResponseStatusException {
        authenticator.refreshToken(accountId.getAccountIdString(), token);
    }

    @Override
    public String generateToken(AccountId accountId) throws ResponseStatusException {
        return authenticator.generateToken(accountId.getAccountIdString());
    }

    @Override
    public void validatePermission(AccountId accountId, Permissions permission) throws ResponseStatusException {
        if(!isPermitted(accountId, permission)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Permitted!");
        }
    }

    @Override
    public void validateTokenAndPermission(AccountId accountId, String token, Permissions permission) throws ResponseStatusException {
        validateToken(accountId, token);
        validatePermission(accountId, permission);
    }

    private boolean isPermitted(AccountId accountId, Permissions permission){
        return accounts.adminExists(accountId) ||
                accounts.getEmployeeIfExists(accountId)
                        .getPermissionsList().contains(permission.name());
    }
}
