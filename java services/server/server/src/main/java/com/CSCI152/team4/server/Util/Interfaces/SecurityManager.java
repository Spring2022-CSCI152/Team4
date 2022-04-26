package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import org.springframework.web.server.ResponseStatusException;

public interface SecurityManager {

    void validateToken(AccountId accountId, String token) throws ResponseStatusException;

    void invalidateToken(AccountId accountId, String token) throws ResponseStatusException;

    void refreshToken(AccountId accountId, String token) throws ResponseStatusException;

    String generateToken(AccountId accountId) throws ResponseStatusException;

    void validatePermission(AccountId accountId, Permissions permission) throws ResponseStatusException;

    void validateTokenAndPermission(AccountId accountId, String token, Permissions permission) throws ResponseStatusException;
}
