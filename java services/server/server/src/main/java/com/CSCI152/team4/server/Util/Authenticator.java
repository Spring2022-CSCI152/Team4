package com.CSCI152.team4.server.Util;

public interface Authenticator {

    //validateToken
    void validateToken(String token, String requestingAccountId);

    String getToken(String accountId);

    void invalidateToken(String token, String requestingAccountId);
}
