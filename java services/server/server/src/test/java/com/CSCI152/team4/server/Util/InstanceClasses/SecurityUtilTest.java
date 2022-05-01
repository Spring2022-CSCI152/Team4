package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Auth.Classes.Token;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.Authenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SecurityUtilTest {

    @Mock
    private Authenticator authenticator;
    @Mock
    private AccountsRepoInterface accounts;
    @Mock
    private AccountId accountId;
    @Mock
    private EmployeeAccount employeeAccount;

    SecurityUtil underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new SecurityUtil(authenticator, accounts);
    }

    @Test
    void itShouldValidateToken() {
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doNothing().when(authenticator).validateToken(idString, token);
        // When
        underTest.validateToken(accountId, token);
        // Then
        verify(authenticator, times(1)).validateToken(idString, token);
        verifyNoMoreInteractions(authenticator);
    }

    @Test
    void itShouldReThrowExceptionOnValidate(){
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doThrow(ResponseStatusException.class).when(authenticator).validateToken(idString, token);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validateToken(accountId, token));

        // Then
        assertThat(e).isInstanceOf(ResponseStatusException.class);
        verify(authenticator,times(1)).validateToken(idString, token);
    }

    @Test
    void itShouldInvalidateToken() {
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doNothing().when(authenticator).invalidateToken(idString, token);
        // When
        underTest.invalidateToken(accountId, token);
        // Then
        verify(authenticator, times(1)).invalidateToken(idString, token);
        verifyNoMoreInteractions(authenticator);
    }

    @Test
    void itShouldRethrowOnInvalidate(){
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doThrow(ResponseStatusException.class).when(authenticator).invalidateToken(idString, token);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.invalidateToken(accountId, token));

        // Then
        assertThat(e).isInstanceOf(ResponseStatusException.class);
        verify(authenticator,times(1)).invalidateToken(idString, token);

    }

    @Test
    void itShouldRefreshToken() {
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doNothing().when(authenticator).refreshToken(idString, token);
        // When
        underTest.refreshToken(accountId, token);
        // Then
        verify(authenticator, times(1)).refreshToken(idString, token);
        verifyNoMoreInteractions(authenticator);
    }

    @Test
    void itShouldRethrowOnRefresh(){
        // Given
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doThrow(ResponseStatusException.class).when(authenticator).refreshToken(idString, token);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.refreshToken(accountId, token));

        // Then
        assertThat(e).isInstanceOf(ResponseStatusException.class);
        verify(authenticator,times(1)).refreshToken(idString, token);

    }
    @Test
    void itShouldGenerateToken() {
        // Given
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        given(authenticator.generateToken(idString)).willReturn(idString);
        // When
        String actual = underTest.generateToken(accountId);
        // Then
        assertEquals(idString, actual);
    }

    @Test
    void itShouldRethrowOnGenerateToken(){
        //Given
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doThrow(ResponseStatusException.class).when(authenticator).generateToken(idString);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.generateToken(accountId));

        //Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class);
    }
    @Test
    void itShouldValidatePermissionOnAdmin() {
        // Given
        Permissions expectedPermission = Permissions.ACCOUNTS_REGISTER;
        given(accounts.adminExists(accountId)).willReturn(true);
        // When
        underTest.validatePermission(accountId, expectedPermission);
        // Then
        verify(accounts, times(1)).adminExists(accountId);
        verifyNoMoreInteractions(accounts);
    }

    @Test
    void itShouldValidatePermissionOnEmployee() {
        // Given
        Permissions expectedPermission = Permissions.ACCOUNTS_REGISTER;
        given(accounts.adminExists(accountId)).willReturn(false);
        given(accounts.getEmployeeIfExists(accountId)).willReturn(employeeAccount);
        given(employeeAccount.getPermissionsList()).willReturn(List.of(Permissions.ACCOUNTS_REGISTER.name()));

        // When
        underTest.validatePermission(accountId, expectedPermission);
        // Then
        verify(accounts, times(1)).adminExists(accountId);
        verify(accounts, times(1)).getEmployeeIfExists(accountId);
        verify(employeeAccount, times(1)).getPermissionsList();
        verifyNoMoreInteractions(accounts, employeeAccount);
    }

    @Test
    void itShouldThrowOnValidatePermission(){
        // Given
        Permissions expectedPermission = Permissions.ACCOUNTS_REGISTER;
        given(accounts.adminExists(accountId)).willReturn(false);
        given(accounts.getEmployeeIfExists(accountId)).willReturn(employeeAccount);
        given(employeeAccount.getPermissionsList()).willReturn(List.of(Permissions.PROFILES_CREATE.name()));

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.validatePermission(accountId, expectedPermission));
        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Not Permitted!");
        verify(accounts, times(1)).adminExists(accountId);
        verify(accounts, times(1)).getEmployeeIfExists(accountId);
        verify(employeeAccount, times(1)).getPermissionsList();
        verifyNoMoreInteractions(accounts, employeeAccount);
    }

    @Test
    void itShouldValidateTokenAndPermission() {
        // Given
        Permissions expectedPermission = Permissions.ACCOUNTS_REGISTER;
        String token = "token";
        String idString = "idString";
        given(accountId.getAccountIdString()).willReturn(idString);
        doNothing().when(authenticator).validateToken(idString, token);
        given(accounts.adminExists(accountId)).willReturn(false);
        given(accounts.getEmployeeIfExists(accountId)).willReturn(employeeAccount);
        given(employeeAccount.getPermissionsList()).willReturn(List.of(Permissions.ACCOUNTS_REGISTER.name()));

        // When
        underTest.validateTokenAndPermission(accountId, token, expectedPermission);
        // Then
        verify(accounts, times(1)).adminExists(accountId);
        verify(accounts, times(1)).getEmployeeIfExists(accountId);
        verify(employeeAccount, times(1)).getPermissionsList();
        verifyNoMoreInteractions(accounts, employeeAccount);
        verify(authenticator, times(1)).validateToken(idString, token);
        verifyNoMoreInteractions(authenticator);

    }
}