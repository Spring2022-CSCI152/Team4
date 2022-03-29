package com.CSCI152.team4.server.Auth.Services;

import com.CSCI152.team4.server.Util.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final TokenManager tokenManager;

    @Autowired
    public AuthenticationService(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    public ResponseEntity<Enum<HttpStatus>> validateToken(String token, String accountId){

        tokenManager.validateToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> refreshToken(String token, String accountId){

        tokenManager.refreshToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> invalidateToken(String token, String accountId){

        tokenManager.invalidateToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
