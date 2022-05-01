package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Auth.Classes.Token;
import com.CSCI152.team4.server.Repos.TokenRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TokenAuthenticatorTest {

    @Mock
    private TokenRepo tokenRepo;

    @Mock
    Token token;

    @Captor
    ArgumentCaptor<Token> tokenArgumentCaptor;

    private Integer expirationInMinutes = 10;

    TokenAuthenticator underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void itShouldRefreshToken() {
        // Given
        // When
        // Then
    }
}