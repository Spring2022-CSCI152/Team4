package com.CSCI152.team4.server.Auth.Services;

import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import com.CSCI152.team4.server.Util.Interfaces.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final Authenticator tokenAuthenticator;

    @Autowired
    public AuthenticationService(Authenticator tokenAuthenticator) {
        this.tokenAuthenticator = tokenAuthenticator;
    }

    public ResponseEntity<Enum<HttpStatus>> validateToken(Request request){

        tokenAuthenticator.validateToken(request.getToken(), request.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> refreshToken(Request request){

        tokenAuthenticator.refreshToken(request.getToken(), request.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> invalidateToken(Request request){

        tokenAuthenticator.invalidateToken(request.getToken(), request.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
