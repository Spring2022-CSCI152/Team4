package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Requests.ProfileFormatUpdateRequestDAO;
import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequestDAO;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SettingsServiceTest {

    @Mock
    private SecurityManager authenticator;
    @Mock
    private SettingsRepoInterface repoManager;
    @Mock
    private AccountsRepoInterface accounts;
    @Mock
    private ReportFormatUpdateRequestDAO reportFormatUpdateRequestDAO;
    @Mock
    private Request request;
    @Mock
    private ProfileFormatUpdateRequestDAO profileFormatUpdateRequestDAO;
    @Mock
    private ReportFormat reportFormat;
    @Mock
    private CustomerProfileFormat profileFormat;
    @Mock
    private BusinessAccount businessAccount;

    @Captor
    ArgumentCaptor<ReportFormat> reportFormatArgumentCaptor;
    @Captor
    ArgumentCaptor<CustomerProfileFormat> profileFormatArgumentCaptor;

    private SettingsService underTest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new SettingsService(authenticator, repoManager, accounts);
    }

    @Test
    void itShouldSetReportFormatForAdmin() {

        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString)); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(reportFormatUpdateRequestDAO.getReportFormat()).willReturn(reportFormat);
        given(reportFormat.getBusinessId()).willReturn(businessId);
        given(repoManager.saveReportFormat(reportFormat)).willReturn(reportFormat);
        // When
        underTest.setReportFormat(reportFormatUpdateRequestDAO);

        // Then
        verify(authenticator, times(1)).validateTokenAndPermission(accountId, token, permission);
        verify(repoManager).saveReportFormat(reportFormatArgumentCaptor.capture());
        assertThat(reportFormatArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(reportFormat);
        verifyNoMoreInteractions(repoManager);

    }
    @Test
    void itShouldSetReportFormatForEmployee() {

        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of()); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of(idString));
        given(reportFormatUpdateRequestDAO.getReportFormat()).willReturn(reportFormat);
        given(reportFormat.getBusinessId()).willReturn(businessId);
        given(repoManager.saveReportFormat(reportFormat)).willReturn(reportFormat);
        // When
        underTest.setReportFormat(reportFormatUpdateRequestDAO);

        // Then
        verify(authenticator, times(1)).validateTokenAndPermission(accountId, token, permission);
        verify(repoManager).saveReportFormat(reportFormatArgumentCaptor.capture());
        assertThat(reportFormatArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(reportFormat);
        verifyNoMoreInteractions(repoManager);

    }

    @Test
    void itShouldThrowOnInvalidBusinessId(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of()); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of());

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setReportFormat(reportFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Invalid Business Id!");
    }

    @Test
    void itShouldThrowOnNullReportFormat(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(reportFormatUpdateRequestDAO.getReportFormat()).willReturn(null);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setReportFormat(reportFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Report Format cannot be null!");
    }

    @Test
    void itShouldThrowOnInvalidReportFormatNoBusinessId(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(reportFormatUpdateRequestDAO.getReportFormat()).willReturn(reportFormat);
        given(reportFormat.getBusinessId()).willReturn(null);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setReportFormat(reportFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Invalid Business Id!");
    }

    @Test
    void itShouldThrowOnDifferentBusinessIds(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(reportFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(reportFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.REPORT_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(reportFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(reportFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(reportFormatUpdateRequestDAO.getReportFormat()).willReturn(reportFormat);
        given(reportFormat.getBusinessId()).willReturn(businessId + 1);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setReportFormat(reportFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Invalid Business Id!");
    }


    @Test
    void itShouldSetProfileFormatForAdmin() {

        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString)); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(profileFormatUpdateRequestDAO.getProfileFormat()).willReturn(profileFormat);
        given(profileFormat.getBusinessId()).willReturn(businessId);
        given(repoManager.saveCustomerProfileFormat(profileFormat)).willReturn(profileFormat);
        // When
        underTest.setProfileFormat(profileFormatUpdateRequestDAO);

        // Then
        verify(authenticator, times(1)).validateTokenAndPermission(accountId, token, permission);
        verify(repoManager).saveCustomerProfileFormat(profileFormatArgumentCaptor.capture());
        assertThat(profileFormatArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(profileFormat);
        verifyNoMoreInteractions(repoManager);

    }
    @Test
    void itShouldSetProfileFormatForEmployee() {

        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of()); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of(idString));
        given(profileFormatUpdateRequestDAO.getProfileFormat()).willReturn(profileFormat);
        given(profileFormat.getBusinessId()).willReturn(businessId);
        given(repoManager.saveCustomerProfileFormat(profileFormat)).willReturn(profileFormat);
        // When
        underTest.setProfileFormat(profileFormatUpdateRequestDAO);

        // Then
        verify(authenticator, times(1)).validateTokenAndPermission(accountId, token, permission);
        verify(repoManager).saveCustomerProfileFormat(profileFormatArgumentCaptor.capture());
        assertThat(profileFormatArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(profileFormat);
        verifyNoMoreInteractions(repoManager);

    }

    @Test
    void itShouldThrowOnInvalidBusinessIdProfileFormat(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of()); // Change to null then add employees
        given(businessAccount.getEmployees()).willReturn(List.of());

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setProfileFormat(profileFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Invalid Business Id!");
    }

    @Test
    void itShouldThrowOnNullProfileFormat(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(profileFormatUpdateRequestDAO.getProfileFormat()).willReturn(null);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setProfileFormat(profileFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Profile Format cannot be null!");
    }

    @Test
    void itShouldThrowOnInvalidProfileFormatNoBusinessId(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(profileFormatUpdateRequestDAO.getProfileFormat()).willReturn(profileFormat);
        given(profileFormat.getBusinessId()).willReturn(null);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setProfileFormat(profileFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Invalid Business Id!");
    }

    @Test
    void itShouldThrowOnDifferentBusinessIdsForProfiles(){
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(profileFormatUpdateRequestDAO.getAccountId()).willReturn(accountId);
        given(profileFormatUpdateRequestDAO.getToken()).willReturn(token);
        Permissions permission = Permissions.PROFILES_FORMAT;
        doNothing().when(authenticator).validateTokenAndPermission(accountId, token, permission);
        given(profileFormatUpdateRequestDAO.getBusinessId()).willReturn(businessId);
        given(profileFormatUpdateRequestDAO.getAccountIdString()).willReturn(idString);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(profileFormatUpdateRequestDAO.getProfileFormat()).willReturn(profileFormat);
        given(profileFormat.getBusinessId()).willReturn(businessId + 1);


        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.setProfileFormat(profileFormatUpdateRequestDAO));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Invalid Business Id!");
    }

    @Test
    void itShouldGetReportFormatForAdmin() {
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);
        given(request.getAccountIdString()).willReturn(idString);
        given(request.getBusinessId()).willReturn(businessId);
        doNothing().when(authenticator).validateToken(accountId, token);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(repoManager.getReportFormatIfExists(businessId)).willReturn(reportFormat);
        // When
        ReportFormat actual = underTest.getReportFormat(request);
        // Then

        assertThat(actual).usingRecursiveComparison().isEqualTo(reportFormat);
        verify(repoManager, times(1)).getReportFormatIfExists(businessId);
        verifyNoMoreInteractions(repoManager);
    }

    @Test
    void itShouldGetReportFormatForEmployee() {
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);
        given(request.getAccountIdString()).willReturn(idString);
        given(request.getBusinessId()).willReturn(businessId);
        doNothing().when(authenticator).validateToken(accountId, token);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of(idString));
        given(repoManager.getReportFormatIfExists(businessId)).willReturn(reportFormat);
        // When
        ReportFormat actual = underTest.getReportFormat(request);
        // Then

        assertThat(actual).usingRecursiveComparison().isEqualTo(reportFormat);
        verify(repoManager, times(1)).getReportFormatIfExists(businessId);
        verifyNoMoreInteractions(repoManager);
    }



    @Test
    void itShouldGetProfileFormatForAdmin() {
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);
        given(request.getAccountIdString()).willReturn(idString);
        given(request.getBusinessId()).willReturn(businessId);
        doNothing().when(authenticator).validateToken(accountId, token);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of(idString));
        given(businessAccount.getEmployees()).willReturn(List.of());
        given(repoManager.getCustomerProfileFormatIfExists(businessId)).willReturn(profileFormat);

        // When
        CustomerProfileFormat actual = underTest.getProfileFormat(request);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(profileFormat);
        verify(repoManager, times(1)).getCustomerProfileFormatIfExists(businessId);
        verifyNoMoreInteractions(repoManager);

    }

    @Test
    void itShouldGetProfileFormatForEmployee() {
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);
        given(request.getAccountIdString()).willReturn(idString);
        given(request.getBusinessId()).willReturn(businessId);
        doNothing().when(authenticator).validateToken(accountId, token);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of(idString));
        given(repoManager.getCustomerProfileFormatIfExists(businessId)).willReturn(profileFormat);

        // When
        CustomerProfileFormat actual = underTest.getProfileFormat(request);

        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(profileFormat);
        verify(repoManager, times(1)).getCustomerProfileFormatIfExists(businessId);
        verifyNoMoreInteractions(repoManager);

    }

    @Test
    void itShouldThrowOnGetProfileInvalidBusinessId() {
        // Given
        Integer businessId = 100;
        String idString = "idString";
        String email = "email";
        String token = "token";
        AccountId accountId = new AccountId(idString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);
        given(request.getAccountIdString()).willReturn(idString);
        given(request.getBusinessId()).willReturn(businessId);
        doNothing().when(authenticator).validateToken(accountId, token);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAdmins()).willReturn(List.of());
        given(businessAccount.getEmployees()).willReturn(List.of());

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getProfileFormat(request));

        // Then
        assertThat(e)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Invalid Business Id!");

    }
}