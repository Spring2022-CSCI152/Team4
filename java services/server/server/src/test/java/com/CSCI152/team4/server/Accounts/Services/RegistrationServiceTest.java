package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Requests.AdminRequest;
import com.CSCI152.team4.server.Accounts.Requests.BusinessRequest;
import com.CSCI152.team4.server.Accounts.Requests.EmployeeRequest;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.InstanceClasses.SettingsRepoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RegistrationServiceTest {

    @Mock
    private AccountsRepoManager repos;
    @Mock
    private SecurityUtil securityManager;
    @Mock
    private SettingsRepoManager settings;

    @Mock
    private BusinessRequest businessRequest;
    @Mock
    private AdminRequest adminRequest;
    @Mock
    private EmployeeRequest employeeRequest;

    @Mock
    AdminAccount adminAccount;
    @Mock
    EmployeeAccount employeeAccount;
    @Mock
    BusinessAccount businessAccount;

    @Captor
    ArgumentCaptor<BusinessAccount> businessAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<AdminAccount> adminAccountArgumentCaptor;
    private RegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new RegistrationService(repos, securityManager, settings);
    }

    @Test
    void itShouldRegisterBusiness() {
        // Given
        Integer businessId = 100;
        String businessName = "TestName";
        String rFirstName = "TFirstName";
        String rLastName = "TLastName";
        String rEmail = "TEmail";
        String rPassword = "TPassword";

        AdminAccount expected = new AdminAccount(rEmail, rPassword, rFirstName, rLastName, "admin");
        BusinessAccount bExpected = new BusinessAccount(businessName, expected.getAccountIdString());

        /*Validate tested in isolation*/
        doNothing().when(businessRequest).validate();
        given(businessRequest.getBusinessName()).willReturn(businessName);
        given(businessRequest.getFirstName()).willReturn(rFirstName);
        given(businessRequest.getLastName()).willReturn(rLastName);
        given(businessRequest.getEmail()).willReturn(rEmail);
        given(businessRequest.getPassword()).willReturn(rPassword);

        given(businessRequest.getAdminAccount()).willReturn(expected);
        given(businessRequest.getBusinessAccount(expected.getAccountIdString())).willReturn(bExpected);

        /*Algo should check for prior reg by using following method*/
        given(repos.getAccountByEmailAndBusinessId(rEmail, businessId)).willReturn(null);
        /*Repos are expected to return the entity the save. BusinessAccount entities
        * set the businessId upon saving. Set behavior to ensure we get desired Id*/
        given(repos.saveBusinessAccount(bExpected)).willReturn(bExpected);

        /*Same as above, saving admin account is expected to return admin account*/
        given(repos.saveAdminAccount(expected)).willReturn(expected);
        /*Report and Profile Formats are expected to be initialized in the process*/
        given(settings.saveReportFormat(any())).willReturn(new ReportFormat(businessId));
        given(settings.saveCustomerProfileFormat(any())).willReturn(new CustomerProfileFormat(businessId));

        /*A token is expected to be generated at this point*/
        given(securityManager.generateToken(expected.getAccountId())).willReturn("token");

        // When
        AdminAccount actual = underTest.registerBusiness(businessRequest);

        // Then
        /*Returned value should be the same as the above admin account
        * except the password and token fields as they are encrypted,
        * set or nulled out during the process*/
        assertThat(actual).usingRecursiveComparison().ignoringFields("password", "token").isEqualTo(expected);
        /* Returned password is expected to be null*/
        assertThat(actual.getPassword()).isNull();
        /* Token is expected to be set before returning*/
        assertThat(actual.getToken()).isEqualTo("token");
        /* the request should be validated, but only once*/
        verify(businessRequest, times(1)).validate();

        /*The business and admin accounts should be generated from the businessRequest*/
        verify(businessRequest, times(1)).getBusinessAccount(expected.getAccountIdString());
        verify(businessRequest, times(1)).getAdminAccount();

        /* A token should be generated*/
        verify(securityManager, times(1))
                .generateToken(expected.getAccountId());

        /*capture and verify business and admin accounts that were to be saved*/
        verify(repos).saveBusinessAccount(businessAccountArgumentCaptor.capture());
        verify(repos).saveAdminAccount(adminAccountArgumentCaptor.capture());
        assertThat(businessAccountArgumentCaptor.getValue()).usingRecursiveComparison().isEqualTo(bExpected);
        assertThat(adminAccountArgumentCaptor.getValue()).usingRecursiveComparison()
                .ignoringFields("password, token").isEqualTo(expected);
        /*Nothing else should happen by this point*/
        verifyNoMoreInteractions(securityManager, repos, securityManager, businessRequest);
    }


    @Test
    void itShouldRegisterAdmin() {
        // Given
        Integer businessId = 100;
        String email = "email";
        String accountIdString = "string";
        String token = "token";
        AccountId accountId = new AccountId(accountIdString, email, businessId);
        given(adminRequest.getAccountId()).willReturn(accountId);
        given(adminRequest.getToken()).willReturn(token);

        Permissions expectedPermission = Permissions.ACCOUNTS_REGISTER;
        /* Authentication should be the first thing to happen*/
        doNothing().when(securityManager).validateTokenAndPermission(accountId, token, expectedPermission);

        /* Request validation should happen next*/
        doNothing().when(adminRequest).validate();

        /*Check for prior reg using email and business id is next*/
        given(adminRequest.getBusinessId()).willReturn(businessId);
        given(adminRequest.getEmail()).willReturn(email);

        /*Get business account to update happens next*/
        given(repos.getBusinessIfExists(businessId)).willReturn(businessAccount);

        /*Getting new admin account from request should happen*/
        given(adminRequest.getAdminAccount()).willReturn(adminAccount);

        /* Get and Store password due to hashing*/
        given(adminAccount.getPassword()).willReturn("pass");
        doNothing().when(adminAccount).setPassword(any());

        /* Then add admin account id string to business account*/
        given(adminAccount.getAccountIdString()).willReturn(accountIdString);
        doNothing().when(businessAccount).addAdmin(accountIdString);

        given(repos.saveBusinessAccount(businessAccount)).willReturn(businessAccount);
        given(repos.saveAdminAccount(adminAccount)).willReturn(adminAccount);

        // When
        ResponseEntity<Enum<HttpStatus>> actual = underTest.registerAdmin(adminRequest);

        // Then
        assertThat(actual).isEqualTo(new ResponseEntity<>(HttpStatus.CREATED));
        verify(adminRequest, times(1)).getAccountId();
        verify(adminRequest, times(1)).getToken();
        verify(securityManager, times(1)).validateTokenAndPermission(accountId, token, expectedPermission);
        verify(adminRequest, times(1)).validate();
        verify(repos, times(1)).getBusinessIfExists(businessId);
        verify(adminRequest, times(1)).getEmail();
        verify(adminRequest, times(1)).getBusinessId();
        verify(adminRequest, times(1)).getAdminAccount();
        verify(adminAccount, times(1)).getPassword();
        verify(adminAccount, times(1)).setPassword(any());
        verify(businessAccount, times(1)).addAdmin(accountIdString);
        verify(repos, times(1)).saveBusinessAccount(businessAccount);
        verify(repos, times(1)).saveAdminAccount(adminAccount);
        verifyNoMoreInteractions(securityManager, adminRequest, repos, adminAccount, businessAccount);
    }

    @Test
    void itShouldRegisterEmployee() {
        // Given
        // When
        // Then
    }
}