package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.LoginRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SuppressWarnings("rawtypes")
class SessionServiceTest {

    @Mock
    private AccountsRepoInterface accounts;
    @Mock
    private SecurityManager securityManager;

    @Mock
    private WorkerAccount account;

    @Mock
    private LoginRequest loginRequest;
    @Mock
    private Request request;

    @Mock
    private BusinessAccount businessAccount;

    private SessionService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new SessionService(securityManager, accounts);
    }

    @Test
    void itShouldLoginAdmin() {

        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";
        String token = "token";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getBusinessId()).willReturn(businessId);
        given(account.getPassword())
                .willReturn(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        given(account.getAccountIdString()).willReturn(accountIdString);
        given(account.getEmail()).willReturn(email);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAccountType(accountIdString)).willReturn(BusinessAccount.adminAccountType);
        given(businessAccount.getAdmins()).willReturn(List.of(accountIdString));
        given(securityManager.generateToken(any())).willReturn(token);
        doNothing().when(account).setToken(token);
        doNothing().when(account).setPassword(null);
        // When
        WorkerAccount actual = underTest.login(loginRequest);
        // Then

        assertThat(actual).usingRecursiveComparison().isEqualTo(account);
    }

    @Test
    void itShouldLoginEmployee() {

        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";
        String token = "token";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getBusinessId()).willReturn(businessId);
        given(account.getPassword())
                .willReturn(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        given(account.getAccountIdString()).willReturn(accountIdString);
        given(account.getEmail()).willReturn(email);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAccountType(accountIdString)).willReturn(BusinessAccount.employeeAccountType);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of(accountIdString));
        given(securityManager.generateToken(any())).willReturn(token);
        doNothing().when(account).setToken(token);
        doNothing().when(account).setPassword(null);
        // When
        WorkerAccount actual = underTest.login(loginRequest);
        // Then

        assertThat(actual).usingRecursiveComparison().isEqualTo(account);
    }

    @Test
    void itShouldThrowErrorOnInvalidPassword() {
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";
        String token = "token";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getPassword()).willReturn(BCrypt.hashpw(password + "SomeOtherValue", BCrypt.gensalt(10))); //This is expected to be encrypted, non encrypted should fail just the same
        given(account.getBusinessId()).willReturn(businessId);

        //When
        Exception  e = assertThrows(ResponseStatusException.class, () -> underTest.login(loginRequest));

        assertThat(e).hasMessageContaining(HttpStatus.BAD_REQUEST.name());
    }

    /*On 4-30-22, this caught logic error on error checking*/
    @Test
    void itShouldThrowErrorOnInvalidBusinessIdForAdmin(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getBusinessId()).willReturn(businessId);
        given(account.getPassword())
                .willReturn(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        given(account.getAccountIdString()).willReturn(accountIdString);
        given(account.getEmail()).willReturn(email);
        given(accounts.getBusinessIfExists(businessId))
                .willReturn(businessAccount);
        given(businessAccount.getAccountType(accountIdString))
                .willReturn(BusinessAccount.adminAccountType);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of());

        //When
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.login(loginRequest));

        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Invalid Business ID");
    }

    @Test
    void itShouldThrowErrorOnInvalidBusinessIdForEmployee(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getBusinessId()).willReturn(businessId);
        given(account.getPassword())
                .willReturn(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        given(account.getAccountIdString()).willReturn(accountIdString);
        given(account.getEmail()).willReturn(email);
        given(accounts.getBusinessIfExists(businessId))
                .willReturn(businessAccount);
        given(businessAccount.getAccountType(accountIdString))
                .willReturn(BusinessAccount.employeeAccountType);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of());

        //When
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.login(loginRequest));

        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Invalid Business ID");
    }

    @Test
    void itShouldThrowErrorOnMissingBusiness(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String accountIdString = "idString";

        given(loginRequest.getEmail()).willReturn(email);
        given(loginRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        given(loginRequest.getPassword()).willReturn(password);
        given(account.getBusinessId()).willReturn(businessId);
        given(account.getPassword())
                .willReturn(BCrypt.hashpw(password, BCrypt.gensalt(10)));
        given(account.getAccountIdString()).willReturn(accountIdString);
        given(account.getEmail()).willReturn(email);
        given(accounts.getBusinessIfExists(businessId))
                .willReturn(null);

        //When
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.login(loginRequest));

        //Then
        assertThat(e)
                .hasMessageContaining("Business Not Found")
                .hasMessageContaining(HttpStatus.NOT_FOUND.name());
    }
    @Test
    void itShouldLogout() {
        // Given
        String token = "token";
        AccountId accountId = new AccountId();
        given(request.getToken()).willReturn(token);
        given(request.getAccountId()).willReturn(accountId);
        doNothing().when(securityManager).invalidateToken(accountId, token);
        // When
        ResponseEntity e = underTest.logout(request);
        // Then

        verify(securityManager, times(1)).invalidateToken(accountId, token);
        verifyNoMoreInteractions(securityManager);

        assertThat(e.toString()).contains(HttpStatus.OK.toString());
    }
}