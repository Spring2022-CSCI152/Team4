package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Repos.RepoManager;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class AccountManagementServiceTest {


    @Mock
    RepoManager repos;

    @Mock
    AccountAuthenticator authenticator;

    @Mock
    AdminAccount admin;

    @Mock
    EmployeeAccount employee;

    ArgumentCaptor<RepoManager> repoManagerArgumentCaptor;

    AccountManagementService underTest;

    AutoCloseable mockOpener;
    @BeforeEach
    void setUp() {

        mockOpener = MockitoAnnotations.openMocks(this);
        underTest = new AccountManagementService(repos, authenticator);

        doNothing().when(authenticator).validateToken(any(), any());

        given(admin.getAccountIdString()).willReturn("adminId");
        given(admin.getEmail()).willReturn("adminEmail");
        given(admin.getBusinessId()).willReturn(123);

        given(admin.getAccountId()).willReturn(
                new AccountId( "adminId",
                        "adminEmail",
                        123));

        given(employee.getAccountIdString()).willReturn("employee");
        given(admin.getEmail()).willReturn("employeeEmail");
        given(admin.getBusinessId()).willReturn(123);

        given(employee.getAccountId()).willReturn(
                new AccountId( "employeeId",
                        "employeeEmail",
                        123));

        doNothing().when(admin).setPassword(any());
        doNothing().when(employee).setPassword(any());

        doReturn(true).when(repos).adminExists(any());
        doReturn(admin).when(repos).saveAdminAccount(any());
        doReturn(admin).when(repos).getAdminIfExists(any());
        doReturn(employee).when(repos).getEmployeeIfExists(any());
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldUpdateAdminAccount() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldUpdateEmployeeAccountFromAdmin() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldUpdateEmployeeAccount() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldGetAdminAccountInfo() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldGetEmployeeAccountInfo() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldGetAdminAccountFromAdmin() {
        // Given
        // When
        // Then
    }

    /*This test uses the supplier interface */
    @Test
    void itShouldGetEmployeeAccountFromAdmin() {
        // Given
        // When
        // Then
        EmployeeAccount expectedReturn = underTest.getEmployeeAccountFromAdmin("", admin.getAccountId(), employee.getAccountId());

        System.out.println(expectedReturn.getAccountId().toString());
        assertThat(expectedReturn).usingRecursiveComparison().ignoringActualEmptyOptionalFields().isEqualTo(employee);
    }

    @Test
    void itShouldUpdateEmployeePermissions() {
        // Given
        // When
        // Then
    }
}