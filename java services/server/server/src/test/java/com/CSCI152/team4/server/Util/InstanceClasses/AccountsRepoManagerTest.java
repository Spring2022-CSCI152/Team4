package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Repos.EmployeeAccountRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountsRepoManagerTest {


    @Mock
    private BusinessAccountRepo business;
    @Mock
    private EmployeeAccountRepo employees;
    @Mock
    private AdminAccountRepo admins;
    @Mock
    private BusinessAccount businessAccount;
    @Mock
    private AdminAccount adminAccount;
    @Mock
    private EmployeeAccount employeeAccount;

    AccountsRepoManager underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new AccountsRepoManager(business, employees ,admins);
    }

    @Test
    void itShouldSaveAdminAccount() {
        // Given
        given(admins.save(adminAccount)).willReturn(adminAccount);
        // When
        AdminAccount actual = underTest.saveAdminAccount(adminAccount);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(adminAccount);
        verify(admins, times(1)).save(adminAccount);
        verifyNoMoreInteractions(admins, adminAccount);
    }

    @Test
    void itShouldSaveEmployeeAccount() {
        // Given
        given(employees.save(employeeAccount)).willReturn(employeeAccount);
        // When
        EmployeeAccount actual = underTest.saveEmployeeAccount(employeeAccount);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(employeeAccount);
        verify(employees, times(1)).save(employeeAccount);
        verifyNoMoreInteractions(employees, employeeAccount);
    }

    @Test
    void itShouldSaveBusinessAccount() {
        // Given
        given(business.save(businessAccount)).willReturn(businessAccount);
        // When
        BusinessAccount actual = underTest.saveBusinessAccount(businessAccount);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(businessAccount);
        verify(business, times(1)).save(businessAccount);
        verifyNoMoreInteractions(business, businessAccount);
    }

    @Test
    void itShouldBusinessExists() {
        // Given
        Integer businessId = 100;
        Integer notBusinessId = 101;
        given(business.existsById(businessId)).willReturn(true);
        given(business.existsById(notBusinessId)).willReturn(false);
        // When
        // Then
        assertTrue(underTest.businessExists(businessId));
        assertFalse(underTest.businessExists(notBusinessId));
        verify(business, times(2)).existsById(any(Integer.class));
        verifyNoMoreInteractions(business);
    }

    @Test
    void itShouldAdminExists() {
        // Given
        AccountId exists = new AccountId("idString", "email", 100);
        AccountId notExists = new AccountId("idString2", "2email", 100);

        given(admins.existsById(exists)).willReturn(true);
        given(admins.existsById(notExists)).willReturn(false);
        // When
        // Then
        assertTrue(underTest.adminExists(exists));
        assertFalse(underTest.adminExists(notExists));
        verify(admins, times(2)).existsById(any(AccountId.class));
        verifyNoMoreInteractions(admins);
    }

    @Test
    void itShouldEmployeeExists() {
        // Given
        AccountId exists = new AccountId("idString", "email", 100);
        AccountId notExists = new AccountId("idString2", "2email", 100);

        given(employees.existsById(exists)).willReturn(true);
        given(employees.existsById(notExists)).willReturn(false);
        // When
        // Then
        assertTrue(underTest.employeeExists(exists));
        assertFalse(underTest.employeeExists(notExists));
        verify(employees, times(2)).existsById(any(AccountId.class));
        verifyNoMoreInteractions(employees);
    }

    @Test
    void itShouldGetBusinessIfExists() {
        // Given
        Integer businessId = 100;
        given(business.existsById(businessId)).willReturn(true);
        given(business.findById(businessId)).willReturn(Optional.of(businessAccount));
        // When
        BusinessAccount actual = underTest.getBusinessIfExists(businessId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(businessAccount);
        verify(business, times(1)).existsById(businessId);
        verify(business, times(1)).findById(businessId);
        verifyNoMoreInteractions(business);
    }

    @Test
    void itShouldThrowOnBusinessNotExist(){
        //Given
        Integer businessId = 100;
        given(business.existsById(businessId)).willReturn(false);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getBusinessIfExists(businessId));

        //Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Account Not Found!");

        verify(business, times(1)).existsById(businessId);
        verifyNoMoreInteractions(business);
    }
    @Test
    void itShouldGetAdminIfExists() {
        // Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(admins.existsById(accountId)).willReturn(true);
        given(admins.findById(accountId)).willReturn(Optional.of(adminAccount));
        // When
        AdminAccount actual = underTest.getAdminIfExists(accountId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(adminAccount);
        verify(admins, times(1)).existsById(accountId);
        verify(admins, times(1)).findById(accountId);
        verifyNoMoreInteractions(admins);
    }

    @Test
    void itShouldThrowOnAdminNotExist(){
        //Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(admins.existsById(accountId)).willReturn(false);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getAdminIfExists(accountId));

        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Account Not Found!");

        verify(admins, times(1)).existsById(accountId);
        verifyNoMoreInteractions(admins);
    }

    @Test
    void itShouldGetEmployeeIfExists() {
        // Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(employees.existsById(accountId)).willReturn(true);
        given(employees.findById(accountId)).willReturn(Optional.of(employeeAccount));
        // When
        EmployeeAccount actual = underTest.getEmployeeIfExists(accountId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(employeeAccount);
        verify(employees, times(1)).existsById(accountId);
        verify(employees, times(1)).findById(accountId);
        verifyNoMoreInteractions(employees);
    }

    @Test
    void itShouldThrowOnEmployeeNotExist(){
        //Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(employees.existsById(accountId)).willReturn(false);

        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getEmployeeIfExists(accountId));

        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Account Not Found!");

        verify(employees, times(1)).existsById(accountId);
        verifyNoMoreInteractions(employees);
    }

    @Test
    void itShouldGetAdminAccountByEmailAndBusinessId() {
        // Given
        String email = "email";
        Integer businessId = 100;
        given(employees.existsByEmailAndBusinessId(email, businessId)).willReturn(false);
        given(admins.existsByEmailAndBusinessId(email, businessId)).willReturn(true);
        given(admins.findTopByEmailAndBusinessId(email, businessId)).willReturn(Optional.of(adminAccount));
        // When
        WorkerAccount actual = underTest.getAccountByEmailAndBusinessId(email, businessId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(adminAccount);
        verify(employees, times(1)).existsByEmailAndBusinessId(email, businessId);
        verify(admins, times(1)).existsByEmailAndBusinessId(email, businessId);
        verify(admins, times(1)).findTopByEmailAndBusinessId(email, businessId);
        verifyNoMoreInteractions(employees, admins);
    }
    @Test
    void itShouldGetEmployeeAccountByEmailAndBusinessId() {
        // Given
        String email = "email";
        Integer businessId = 100;
        given(employees.existsByEmailAndBusinessId(email, businessId)).willReturn(true);
        given(employees.findTopByEmailAndBusinessId(email, businessId))
                .willReturn(Optional.of(employeeAccount));
        // When
        WorkerAccount actual = underTest.getAccountByEmailAndBusinessId(email, businessId);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(employeeAccount);
        verify(employees, times(1)).existsByEmailAndBusinessId(email, businessId);
        verify(employees, times(1)).findTopByEmailAndBusinessId(email, businessId);
        verifyNoMoreInteractions(employees);
        verifyNoInteractions(admins);
    }

    @Test
    void itShouldThrowByEmailAndBusinessId() {
        // Given
        String email = "email";
        Integer businessId = 100;
        given(employees.existsByEmailAndBusinessId(email, businessId)).willReturn(false);
        given(admins.existsByEmailAndBusinessId(email, businessId)).willReturn(false);
        // When
        Exception e =
                assertThrows(ResponseStatusException.class,
                        () -> underTest.getAccountByEmailAndBusinessId(email, businessId));
        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Account Not Found!");

        verify(employees, times(1)).existsByEmailAndBusinessId(email, businessId);
        verify(admins, times(1)).existsByEmailAndBusinessId(email, businessId);
        verifyNoMoreInteractions(employees, admins);
    }

    @Test
    void itShouldThrowOnAccountNotPresent(){
        //Given
        String email = "email";
        Integer businessId = 100;
        given(employees.existsByEmailAndBusinessId(email, businessId)).willReturn(true);
        given(employees.findTopByEmailAndBusinessId(email, businessId))
                .willReturn(Optional.empty());

        // When
        Exception e =
                assertThrows(ResponseStatusException.class,
                        () -> underTest.getAccountByEmailAndBusinessId(email, businessId));
        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Account Not Found!");

        verify(employees, times(1)).existsByEmailAndBusinessId(email, businessId);
        verify(employees, times(1)).findTopByEmailAndBusinessId(email, businessId);
        verifyNoMoreInteractions(employees, admins);
    }
    @Test
    void itShouldGetAccountsByBusinessId() {
        // Given
        Integer businessId = 100;
        given(admins.findAllByBusinessId(businessId)).willReturn(List.of(adminAccount));
        given(employees.findAllByBusinessId(businessId)).willReturn(List.of(employeeAccount));
        doNothing().when(adminAccount).setPassword(null);
        doNothing().when(employeeAccount).setPassword(null);

        // When
        List<WorkerAccount> actual = underTest.getAccountsByBusinessId(businessId);
        // Then
        assertThat(actual).asList().containsExactlyInAnyOrder(adminAccount, employeeAccount);
        verify(adminAccount, times(1)).setPassword(null);
        verify(employeeAccount, times(1)).setPassword(null);
        verify(admins, times(1)).findAllByBusinessId(businessId);
        verify(employees, times(1)).findAllByBusinessId(businessId);
        verifyNoMoreInteractions(admins, employees);

    }

    @Test
    void itShouldRemoveEmployeeAccount() {
        // Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(employees.existsById(accountId)).willReturn(false);
        doNothing().when(employees).deleteById(accountId);
        // When
        // Then
        assertTrue(underTest.removeEmployeeAccount(accountId));

        verify(employees, times(1)).deleteById(accountId);
        verify(employees, times(1)).existsById(accountId);
        verifyNoMoreInteractions(employees);
    }

    @Test
    void itShouldRemoveAdminAccount() {
        // Given
        AccountId accountId = new AccountId("idString", "email", 100);
        given(admins.existsById(accountId)).willReturn(false);
        doNothing().when(admins).deleteById(accountId);
        // When
        // Then
        assertTrue(underTest.removeAdminAccount(accountId));

        verify(admins, times(1)).deleteById(accountId);
        verify(admins, times(1)).existsById(accountId);
        verifyNoMoreInteractions(admins);
    }

    @Test
    void itShouldThrowOnNullAdminIdRemoval(){
        //Given
        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.removeAdminAccount(null));
        //Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("AccountId must not be null!");
        verifyNoMoreInteractions(admins);
    }

    @Test
    void itShouldThrowOnNullEmployeeIdRemoval(){
        //Given
        //When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.removeEmployeeAccount(null));
        //Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.BAD_REQUEST.name())
                .hasMessageContaining("AccountId must not be null!");
        verifyNoMoreInteractions(employees);
    }
}