package com.CSCI152.team4.server.Auth.Services;

import com.CSCI152.team4.server.Util.InstanceClasses.RequestDAO;
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

    public ResponseEntity<Enum<HttpStatus>> validateToken(RequestDAO requestDAO){

        tokenAuthenticator.validateToken(requestDAO.getToken(), requestDAO.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> refreshToken(RequestDAO requestDAO){

        tokenAuthenticator.refreshToken(requestDAO.getToken(), requestDAO.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Enum<HttpStatus>> invalidateToken(RequestDAO requestDAO){

        tokenAuthenticator.invalidateToken(requestDAO.getToken(), requestDAO.getAccountIdString());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
