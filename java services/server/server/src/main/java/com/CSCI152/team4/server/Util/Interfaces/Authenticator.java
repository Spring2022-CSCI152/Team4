package com.CSCI152.team4.server.Util.Interfaces;

import org.springframework.web.server.ResponseStatusException;

public interface Authenticator {

    //validateToken
    void validateToken(String accountId, String token) throws ResponseStatusException;

    String generateToken(String accountId);

    void invalidateToken(String accountId, String token) throws ResponseStatusException;

    void refreshToken(String accountId, String token) throws ResponseStatusException;
}
