package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Requests.AdminRequest;
import com.CSCI152.team4.server.Accounts.Requests.BusinessRequest;
import com.CSCI152.team4.server.Accounts.Requests.EmployeeRequest;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.InstanceClasses.SettingsRepoManager;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
        // When
        // Then
    }

    @Test
    void itShouldRegisterEmployee() {
        // Given
        // When
        // Then
    }
}