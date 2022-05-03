package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequestDAO;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequestDAO;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountUpdaterTest {

    @Mock
    private AccountsRepoInterface accounts;

    @Mock
    private BusinessAccount businessAccount;

    @Mock
    private AdminAccount adminAccount;

    @Mock
    private EmployeeAccount employeeAccount;

    @Mock
    private UpdateRequestDAO updateRequest;

    @Mock
    private UpdateOtherRequestDAO updateOtherRequest;


    private AccountUpdater underTest;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountUpdater(accounts);
    }

    @Test
    void itShouldUpdateOtherAdmin() {
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String fName = "firstName";
        String lName = "lastName";
        String jobTitle = "Admin";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateOtherRequest.getTargetId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn(BusinessAccount.adminAccountType);
        given(accounts.getAdminIfExists(targetId)).willReturn(adminAccount);
        given(updateOtherRequest.getFirstName()).willReturn(fName);
        given(updateOtherRequest.getLastName()).willReturn(lName);
        given(updateOtherRequest.getPassword()).willReturn(password);
        given(updateOtherRequest.getJobTitle()).willReturn(jobTitle);
        doNothing().when(adminAccount).setFirstName(fName);
        doNothing().when(adminAccount).setLastName(lName);
        doNothing().when(adminAccount).setJobTitle(jobTitle);
        doNothing().when(adminAccount).setPassword(any());
        given(accounts.saveAdminAccount(adminAccount)).willReturn(adminAccount);
        doNothing().when(adminAccount).setPassword(null);

        // When
        WorkerAccount actual = underTest.updateOther(updateOtherRequest);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(adminAccount);
        verify(adminAccount, times(1)).setFirstName(fName);
        verify(adminAccount, times(1)).setLastName(lName);
        verify(adminAccount, times(1)).setJobTitle(jobTitle);
        /*Set once to new password, set null on return*/
        verify(adminAccount, times(2)).setPassword(any());
        verify(accounts, times(1)).saveAdminAccount(adminAccount);
        verify(accounts, times(1)).getBusinessIfExists(businessId);
        verify(accounts, times(1)).getAdminIfExists(targetId);
        verifyNoMoreInteractions(adminAccount, accounts);
    }

    @Test
    void itShouldUpdateOtherEmployee(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String fName = "firstName";
        String lName = "lastName";
        String jobTitle = "employee";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateOtherRequest.getTargetId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn(BusinessAccount.employeeAccountType);
        given(accounts.getEmployeeIfExists(targetId)).willReturn(employeeAccount);
        given(updateOtherRequest.getFirstName()).willReturn(fName);
        given(updateOtherRequest.getLastName()).willReturn(lName);
        given(updateOtherRequest.getPassword()).willReturn(password);
        given(updateOtherRequest.getJobTitle()).willReturn(jobTitle);
        doNothing().when(employeeAccount).setFirstName(fName);
        doNothing().when(employeeAccount).setLastName(lName);
        doNothing().when(employeeAccount).setJobTitle(jobTitle);
        doNothing().when(employeeAccount).setPassword(any());
        given(accounts.saveEmployeeAccount(employeeAccount)).willReturn(employeeAccount);
        doNothing().when(employeeAccount).setPassword(null);

        // When
        WorkerAccount actual = underTest.updateOther(updateOtherRequest);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employeeAccount);
        verify(employeeAccount, times(1)).setFirstName(fName);
        verify(employeeAccount, times(1)).setLastName(lName);
        verify(employeeAccount, times(1)).setJobTitle(jobTitle);
        /*Set once to new password, set null on return*/
        verify(employeeAccount, times(2)).setPassword(any());
        verify(accounts, times(1)).saveEmployeeAccount(employeeAccount);
        verify(accounts, times(1)).getBusinessIfExists(businessId);
        verify(accounts, times(1)).getEmployeeIfExists(targetId);
        verifyNoMoreInteractions(employeeAccount, accounts);
    }

    @Test
    void itShouldUpdateNoneOnNull(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateOtherRequest.getTargetId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn(BusinessAccount.employeeAccountType);
        given(accounts.getEmployeeIfExists(targetId)).willReturn(employeeAccount);
        given(updateOtherRequest.getFirstName()).willReturn(null);
        given(updateOtherRequest.getLastName()).willReturn(null);
        given(updateOtherRequest.getPassword()).willReturn(null);
        given(updateOtherRequest.getJobTitle()).willReturn(null);
        given(accounts.saveEmployeeAccount(employeeAccount)).willReturn(employeeAccount);
        doNothing().when(employeeAccount).setPassword(null);

        // When
        WorkerAccount actual = underTest.updateOther(updateOtherRequest);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employeeAccount);
        /*Set once to new password, set null on return*/
        verify(employeeAccount, times(1)).setPassword(any());
        verify(accounts, times(1)).saveEmployeeAccount(employeeAccount);
        verify(accounts, times(1)).getBusinessIfExists(businessId);
        verify(accounts, times(1)).getEmployeeIfExists(targetId);
        verifyNoMoreInteractions(employeeAccount, accounts);
    }

    @Test
    void itShouldUpdateNoneOnBlank(){
        // Given
        Integer businessId = 100;
        String email = "email";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateOtherRequest.getTargetId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn(BusinessAccount.employeeAccountType);
        given(accounts.getEmployeeIfExists(targetId)).willReturn(employeeAccount);
        given(updateOtherRequest.getFirstName()).willReturn("");
        given(updateOtherRequest.getLastName()).willReturn("");
        given(updateOtherRequest.getPassword()).willReturn("");
        given(updateOtherRequest.getJobTitle()).willReturn("");
        given(accounts.saveEmployeeAccount(employeeAccount)).willReturn(employeeAccount);
        doNothing().when(employeeAccount).setPassword(null);

        // When
        WorkerAccount actual = underTest.updateOther(updateOtherRequest);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employeeAccount);
        /*Set once to new password, set null on return*/
        verify(employeeAccount, times(1)).setPassword(any());
        verify(accounts, times(1)).saveEmployeeAccount(employeeAccount);
        verify(accounts, times(1)).getBusinessIfExists(businessId);
        verify(accounts, times(1)).getEmployeeIfExists(targetId);
        verifyNoMoreInteractions(employeeAccount, accounts);
    }

    @Test
    void itShouldThrowOnInvalidAccountType(){
        //Given
        Integer businessId = 100;
        String email = "email";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateOtherRequest.getTargetId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn("Invalid");

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.updateOther(updateOtherRequest));

        assertThat(e)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("Invalid Account Type!");

    }

    @Test
    void itShouldUpdateSelf() {
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String fName = "firstName";
        String lName = "lastName";
        String jobTitle = "Admin";
        String idString = "idString";
        AccountId targetId = new AccountId(idString, email, businessId);
        given(updateRequest.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(updateRequest.getAccountId()).willReturn(targetId);
        given(businessAccount.getAccountType(idString)).willReturn(BusinessAccount.adminAccountType);
        given(accounts.getAdminIfExists(targetId)).willReturn(adminAccount);
        given(updateRequest.getFirstName()).willReturn(fName);
        given(updateRequest.getLastName()).willReturn(lName);
        given(updateRequest.getPassword()).willReturn(password);
        given(updateRequest.getJobTitle()).willReturn(jobTitle);
        doNothing().when(adminAccount).setFirstName(fName);
        doNothing().when(adminAccount).setLastName(lName);
        doNothing().when(adminAccount).setJobTitle(jobTitle);
        doNothing().when(adminAccount).setPassword(any());
        given(accounts.saveAdminAccount(adminAccount)).willReturn(adminAccount);
        doNothing().when(adminAccount).setPassword(null);

        // When
        WorkerAccount actual = underTest.updateSelf(updateRequest);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(adminAccount);
        verify(adminAccount, times(1)).setFirstName(fName);
        verify(adminAccount, times(1)).setLastName(lName);
        verify(adminAccount, times(1)).setJobTitle(jobTitle);
        /*Set once to new password, set null on return*/
        verify(adminAccount, times(2)).setPassword(any());
        verify(accounts, times(1)).saveAdminAccount(adminAccount);
        verify(accounts, times(1)).getBusinessIfExists(businessId);
        verify(accounts, times(1)).getAdminIfExists(targetId);
        verifyNoMoreInteractions(adminAccount, accounts);

    }
}