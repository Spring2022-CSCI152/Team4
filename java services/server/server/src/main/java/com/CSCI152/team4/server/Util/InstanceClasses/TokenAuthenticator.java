package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Auth.Classes.Token;
import com.CSCI152.team4.server.Repos.TokenRepo;
import com.CSCI152.team4.server.Util.Interfaces.Authenticator;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Component
public class TokenAuthenticator implements Authenticator {

    private final TokenRepo repo;

    private final String secret;

    private final Integer expirationInMins;


    @Autowired
    public TokenAuthenticator(TokenRepo repo, @Value("${frmw.secret}") String secret, @Value("${frmw.expirationTimeInMinutes}") Integer expirationInMinutes) {
        this.repo = repo;
        this.secret = secret;
        this.expirationInMins = expirationInMinutes;
    }

    @Override
    public void validateToken(String accountId, String token)
    {
        Token persistedToken = getTokenIfExists(token);

        if(persistedToken.isExpired()
            || !persistedToken.getAccountId().equals(accountId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Token has been invalidated!", new Exception());
        }
        else{
            /*Prevents auto timeout after 10 minutes, and allows them to take another
            * 10 minutes of inaction to be invalid*/
            refreshToken(persistedToken.getAccountId(), persistedToken.getToken());
        }

    }

    @Override
    public String generateToken(String accountId) {
        return generateAndSaveToken(accountId);
    }

    private String generateAndSaveToken(String accountId){

        String token = DigestUtils.sha256Hex(accountId + secret + now());
        Token persist = new Token(token, accountId,
                Timestamp.valueOf(now()),
                Timestamp.valueOf(now().plusMinutes(expirationInMins)));

        repo.save(persist);

        return token;
    }

    @Override
    public void invalidateToken(String accountId, String token) {
        Token t = getTokenIfExists(token);
        if(t.getAccountId().equals(accountId)
                || t.isExpired()){
            repo.delete(t);
        }
    }

    @Override
    public void refreshToken(String accountId, String token){

        Token toRefresh = getTokenIfExists(token);
        if(!toRefresh.getAccountId().equals(accountId) || toRefresh.isExpired()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to refresh token!");
        }

        toRefresh.setExp(Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationInMins)));
        repo.save(toRefresh);
    }

    private Token getTokenIfExists(String token){

        if(repo.existsById(token)) {
            return repo.findById(token).get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No token found!");
    }

}