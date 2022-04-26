package com.CSCI152.team4.server.Auth.Classes;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import static java.time.LocalDateTime.now;

@Entity
@Table(name="Tokens")
public class Token {

    @Id
    @NotNull
    private String token;

    @NotBlank
    private String accountId;

    private Timestamp iss;

    private Timestamp exp;

    public Token() {
    }

    public Token(String token, String accountId, Timestamp iss, Timestamp exp) {
        this.token = token;
        this.accountId = accountId;
        this.iss = iss;
        this.exp = exp;
    }

    public boolean isExpired(){
        return Timestamp.valueOf(now()).after(exp);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Timestamp getIss() {
        return iss;
    }

    public void setIss(Timestamp iss) {
        this.iss = iss;
    }

    public Timestamp getExp() {
        return exp;
    }

    public void setExp(Timestamp exp) {
        this.exp = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token1 = (Token) o;
        return getToken().equals(token1.getToken())
                && getAccountId().equals(token1.getAccountId())
                && getIss().equals(token1.getIss())
                && getExp().equals(token1.getExp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getAccountId(), getIss(), getExp());
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", accountId='" + accountId + '\'' +
                ", iss=" + iss +
                ", exp=" + exp +
                '}';
    }
}
