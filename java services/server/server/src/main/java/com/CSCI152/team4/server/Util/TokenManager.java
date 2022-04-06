package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Auth.Classes.Token;
import com.CSCI152.team4.server.Auth.Repos.TokenRepo;
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
public class TokenManager implements Authenticator {

    private final TokenRepo repo;

    private final String secret;

    private final Integer expirationInMins;


    @Autowired
    public TokenManager(TokenRepo repo, @Value("${frmw.secret}") String secret, @Value("${frmw.expirationTimeInMinutes}") Integer expirationInMinutes) {
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

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token has been invalidated!");
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
                Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationInMins)));

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

        if(toRefresh.getAccountId().equals(accountId) && !toRefresh.isExpired()){
            toRefresh.setExp(Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationInMins)));

            repo.save(toRefresh);

        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to refresh token!");
    }

    private Token getTokenIfExists(String token){
        Optional<Token> optional =  repo.findById(token);

        if(optional.isPresent()) {
            return optional.get();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No token found!");
    }

}