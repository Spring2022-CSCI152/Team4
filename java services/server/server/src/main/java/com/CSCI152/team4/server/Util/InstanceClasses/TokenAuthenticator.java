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
    public void validateToken(String token, String requestAccountId)
    {
        Token persistedToken = getTokenIfExists(token);

        if(persistedToken.isExpired()
            || !persistedToken.getAccountId().equals(requestAccountId)){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token has been invalidated!", new Exception());
        }

    }

    @Override
    public String getToken(String accountId) {
        return generateAndSaveToken(accountId);
    }

    private String generateAndSaveToken(String accountId){

        String token = DigestUtils.sha256Hex(accountId + secret);
        Token persist = new Token(token, accountId,
                Timestamp.valueOf(now()),
                Timestamp.valueOf(now().plusMinutes(expirationInMins)));

        repo.save(persist);

        return token;
    }

    @Override
    public void invalidateToken(String token, String requestAccountId) {
        Token t = getTokenIfExists(token);
        if(t.getAccountId().equals(requestAccountId)
                || t.isExpired()){
            repo.delete(t);
        }
    }

    public void refreshToken(String token, String accountId){

        Token toRefresh = getTokenIfExists(token);
        System.out.println(toRefresh);
        System.out.println((toRefresh.isExpired()) ? "Expired" : "Valid");
        if(!toRefresh.getAccountId().equals(accountId) || toRefresh.isExpired()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to refresh token!", new Exception());
        }

        toRefresh.setExp(Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationInMins)));
        repo.save(toRefresh);
    }

    private Token getTokenIfExists(String token){
        Optional<Token> optional =  repo.findById(token);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No token found!", new Exception());
    }

}