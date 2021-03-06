package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Auth.Classes.Token;
import com.CSCI152.team4.server.Repos.TokenRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TokenAuthenticatorTest {

    @Mock
    private TokenRepo tokenRepo;

    @Mock
    Token token;

    @Captor
    ArgumentCaptor<Token> tokenArgumentCaptor;

    TokenAuthenticator underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        Integer expirationInMinutes = 10;
        underTest = new TokenAuthenticator(tokenRepo, "secret", expirationInMinutes);
    }

    @Test
    void itShouldValidateToken() {
        // Given
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(false);
        given(token.getAccountId()).willReturn(accountId);
        given(token.getToken()).willReturn(tokenString);
        doNothing().when(token).setExp(any(Timestamp.class));
        given(tokenRepo.save(token)).willReturn(token);
        // When
        underTest.validateToken(accountId, tokenString);
        // Then
        verify(tokenRepo).save(tokenArgumentCaptor.capture());
        verify(tokenRepo, times(1)).save(token);
        verify(token, times(1)).setExp(any(Timestamp.class));
        verify(token, times(2)).isExpired();
        assertThat(tokenArgumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(token);
    }

    @Test
    void itShouldThrowOnNoTokenFound(){
        //Given
        String accountId = "idString";
        String token = "token";
        given(tokenRepo.existsById(token)).willReturn(false);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validateToken(accountId, token));

        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("No token found!");

    }
    @Test
    void itShouldThrowErrorOnExpiredValidateToken() {
        // Given
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(true);
        given(token.getAccountId()).willReturn(accountId);
        given(token.getToken()).willReturn(tokenString);
        doNothing().when(token).setExp(any(Timestamp.class));
        given(tokenRepo.save(token)).willReturn(token);
        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validateToken(accountId, tokenString));
        // Then

        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Token has been invalidated!");
    }

    @Test
    void itShouldGenerateToken() {
        // Given
        String accountId = "idString";
        // When
        String actual = underTest.generateToken(accountId);

        // Then
        verify(tokenRepo).save(tokenArgumentCaptor.capture());
        assertThat(tokenArgumentCaptor.getValue().getToken()).isEqualTo(actual);
        verify(tokenRepo, times(1)).save(any(Token.class));
        verifyNoMoreInteractions(tokenRepo);
    }

    @Test
    void itShouldInvalidateToken() {
        // Given
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(false);
        given(token.getAccountId()).willReturn(accountId);
        given(token.getToken()).willReturn(tokenString);
        // When
        underTest.invalidateToken(accountId, tokenString);
        // Then
        verify(tokenRepo).delete(tokenArgumentCaptor.capture());

    }

    @Test
    void itShouldInvalidateExpiredToken() {
        // Given
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(true);
        given(token.getAccountId()).willReturn(accountId + "some");
        given(token.getToken()).willReturn(tokenString);
        // When
        underTest.invalidateToken(accountId, tokenString);
        // Then
        verify(tokenRepo).delete(tokenArgumentCaptor.capture());

    }

    @Test
    void itShouldRefreshToken() {
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(false);
        given(token.getAccountId()).willReturn(accountId);
        given(token.getToken()).willReturn(tokenString);
        doNothing().when(token).setExp(any(Timestamp.class));
        given(tokenRepo.save(token)).willReturn(token);
        // When
        underTest.refreshToken(accountId, tokenString);
        // Then
        verify(tokenRepo).save(tokenArgumentCaptor.capture());
        verify(tokenRepo, times(1)).save(token);
        verify(token, times(1)).setExp(any(Timestamp.class));
        verify(token, times(1)).isExpired();
        assertThat(tokenArgumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(token);
    }

    @Test
    void itShouldThrowExceptionOnExpiredTokenRefresh(){
        String tokenString = "token";
        String accountId = "idString";
        given(tokenRepo.existsById(tokenString)).willReturn(true);
        given(tokenRepo.findById(tokenString)).willReturn(Optional.of(token));
        given(token.isExpired()).willReturn(true);
        given(token.getAccountId()).willReturn(accountId);
        given(token.getToken()).willReturn(tokenString);
        doNothing().when(token).setExp(any(Timestamp.class));
        given(tokenRepo.save(token)).willReturn(token);
        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () ->underTest.refreshToken(accountId, tokenString));
        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Unable to refresh token!");
    }
}