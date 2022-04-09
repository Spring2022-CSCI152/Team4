package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class RegistrationServiceTest {

    @Mock
    AccountsRepoManager repoManager;

    @Mock
    TokenAuthenticator tokenAuthenticator;

    @Mock
    BusinessAccount businessAccount;

    @Mock
    AdminAccount adminAccount;

    @Mock
    EmployeeAccount employeeAccount;

    @Captor
    ArgumentCaptor<EmployeeAccount> employeeAccountArgumentCaptor;

    @Captor
    ArgumentCaptor<AdminAccount> adminAccountArgumentCaptor;

    @Captor
    ArgumentCaptor<BusinessAccount> businessAccountArgumentCaptor;

    RegistrationService underTest;

    AutoCloseable mockOpener;

    @BeforeEach
    void setUp() {
        mockOpener = MockitoAnnotations.openMocks(this);
        setRepoManagerStubs();
        setTokenManagerStubs();
        setBusinessAccountStubs();
        setAdminAccountStubs();
        setEmployeeAccountStubs();
    }

    private void setRepoManagerStubs(){
        doReturn(businessAccount).when(repoManager).saveBusinessAccount(any());
        doReturn(employeeAccount).when(repoManager).saveEmployeeAccount(any());
        doReturn(adminAccount).when(repoManager).saveAdminAccount(any());
    }

    private void setTokenManagerStubs(){
        doReturn("someToken").when(tokenAuthenticator).getToken(any());
        doNothing().when(tokenAuthenticator).validateToken(any(), any());
    }

    private void setBusinessAccountStubs(){
        given(businessAccount.getBusinessName()).willReturn("businessName");
        given(businessAccount.getBusinessId()).willReturn(100);
        doNothing().when(businessAccount).addEmployee(any());
        doNothing().when(businessAccount).addAdmin(any());
    }

    private void setAdminAccountStubs(){
        doReturn("adminIdString").when(adminAccount).getAccountIdString();
        doReturn(100).when(adminAccount).getBusinessId();
        doReturn("adminEmail").when(adminAccount).getEmail();
        doReturn(new AccountId("adminIdString", "adminEmail", 100)).when(adminAccount).getAccountId();
        doReturn("adminFName").when(adminAccount).getFirstName();
        doReturn("adminLName").when(adminAccount).getLastName();
        doReturn("Admin").when(adminAccount).getJobTitle();
        doReturn("somePassword").when(adminAccount).getPassword();
        doNothing().when(adminAccount).setPassword(any());

    }

    private void setEmployeeAccountStubs(){
        doReturn("employeeIdString").when(employeeAccount).getAccountIdString();
        doReturn(100).when(employeeAccount).getBusinessId();
        doReturn("employeeEmail").when(employeeAccount).getEmail();
        doReturn(new AccountId("employeeIdString", "employeeEmail", 100)).when(adminAccount).getAccountId();
        doReturn("employeeFName").when(employeeAccount).getFirstName();
        doReturn("employeeLName").when(employeeAccount).getLastName();
        doReturn("Employee").when(employeeAccount).getJobTitle();
        doReturn("somePassword").when(employeeAccount).getPassword();
        doNothing().when(employeeAccount).setPassword(any());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldRegisterBusiness() {
        // Given
        // When
        // Then
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