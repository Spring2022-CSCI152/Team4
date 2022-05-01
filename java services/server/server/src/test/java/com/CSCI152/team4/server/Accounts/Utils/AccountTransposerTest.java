package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

class AccountTransposerTest {

    AccountTransposer underTest;

    @Mock
    AdminAccount adminAccount;

    @Mock
    EmployeeAccount employeeAccount;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        underTest = new AccountTransposer();
    }

    @Test
    void itShouldTransposeToEmployee() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String fName = "firstName";
        String lName = "lastName";
        String jobTitle = "Admin";
        String idString = "idString";
        given(adminAccount.getAccountIdString()).willReturn(idString);
        given(adminAccount.getBusinessId()).willReturn(businessId);
        given(adminAccount.getEmail()).willReturn(email);
        given(adminAccount.getPassword()).willReturn(password);
        given(adminAccount.getFirstName()).willReturn(fName);
        given(adminAccount.getLastName()).willReturn(lName);
        given(adminAccount.getJobTitle()).willReturn(jobTitle);

        EmployeeAccount expected =  new EmployeeAccount(businessId, email, password, fName, lName, jobTitle);
        expected.setAccountIdString(idString);
        // When
        WorkerAccount actual = underTest.transposeTo(EmployeeAccount.class, adminAccount);
        // Then
        assertThat(actual).isInstanceOf(EmployeeAccount.class);
        assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp", "permissions")
                .isEqualTo(expected);
    }


    @Test
    void itShouldTransposeToAdmin() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        // Given
        Integer businessId = 100;
        String email = "email";
        String password = "password";
        String fName = "firstName";
        String lName = "lastName";
        String jobTitle = "Admin";
        String idString = "idString";
        given(employeeAccount.getAccountIdString()).willReturn(idString);
        given(employeeAccount.getBusinessId()).willReturn(businessId);
        given(employeeAccount.getEmail()).willReturn(email);
        given(employeeAccount.getPassword()).willReturn(password);
        given(employeeAccount.getFirstName()).willReturn(fName);
        given(employeeAccount.getLastName()).willReturn(lName);
        given(employeeAccount.getJobTitle()).willReturn(jobTitle);

        AdminAccount expected =  new AdminAccount(businessId, email, password, fName, lName, jobTitle);
        expected.setAccountId(idString);
        // When
        WorkerAccount actual = underTest.transposeTo(AdminAccount.class, employeeAccount);
        // Then
        assertThat(actual).isInstanceOf(AdminAccount.class);
        assertThat(actual).usingRecursiveComparison().ignoringFields("timestamp")
                .isEqualTo(expected);
    }
}