package com.CSCI152.team4.server.Auth.Services;

import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final TokenAuthenticator tokenAuthenticator;

    @Autowired
    public AuthenticationService(TokenAuthenticator tokenAuthenticator) {
        this.tokenAuthenticator = tokenAuthenticator;
    }

    public ResponseEntity<Enum<HttpStatus>> validateToken(String token, String accountId){

        tokenAuthenticator.validateToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> refreshToken(String token, String accountId){

        tokenAuthenticator.refreshToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> invalidateToken(String token, String accountId){

        tokenAuthenticator.invalidateToken(token, accountId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
