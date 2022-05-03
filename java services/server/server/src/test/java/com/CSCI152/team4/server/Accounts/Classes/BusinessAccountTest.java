package com.CSCI152.team4.server.Accounts.Classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

class BusinessAccountTest {

    @Mock
    AdminAccount mockedAdmin;

    @Mock
    EmployeeAccount mockedEmployee;

    BusinessAccount underTest;

    AutoCloseable mockOpener;

    @BeforeEach
    void setUp(){
        mockOpener = MockitoAnnotations.openMocks(this);

        given(mockedAdmin.getAccountIdString()).willReturn("adminId");
        given(mockedEmployee.getAccountIdString()).willReturn("employeeId");

        underTest = new BusinessAccount("testing", mockedAdmin.getAccountIdString());
        underTest.setBusinessId(123);

        doReturn(underTest.getBusinessId()).when(mockedAdmin).getBusinessId();
        doReturn(underTest.getBusinessId()).when(mockedEmployee).getBusinessId();
    }


    @Test
    void itShouldAddAdmin(){

        String newAdminIdString = "newAdmin";
        underTest.addAdmin(newAdminIdString);

        assertThat(underTest.getAdmins()).asList()
                .containsExactlyInAnyOrder(
                        mockedAdmin.getAccountIdString(),
                        newAdminIdString
                );
    }

    @Test
    void itShouldRemoveAdmin(){
        String newAdminIdString = "newAdmin";
        underTest.addAdmin(newAdminIdString);

        underTest.removeAdmin(newAdminIdString);

        assertThat(underTest.getAdmins()).asList()
                .containsExactlyInAnyOrder(
                        mockedAdmin.getAccountIdString()
                );
    }

    @Test
    void itShouldAddEmployee(){

        underTest.addEmployee(mockedEmployee.getAccountIdString());

        assertThat(underTest.getEmployees()).asList()
                .containsExactlyInAnyOrder(
                        mockedEmployee.getAccountIdString()
                );
    }

    @Test
    void itShouldRemoveEmployee(){

        underTest.addEmployee(mockedEmployee.getAccountIdString());

        underTest.removeEmployee(mockedEmployee.getAccountIdString());

        assertThat(underTest.getEmployees()).asList()
                .containsExactlyInAnyOrder();
    }

    @Test
    void itShouldPromoteEmployee(){

        underTest.addEmployee(mockedEmployee.getAccountIdString());

        underTest.promote(mockedEmployee.getAccountIdString());

        assertThat(underTest.getEmployees()).asList().containsExactly();

        assertThat(underTest.getAdmins()).asList()
                .containsExactlyInAnyOrder(
                        mockedAdmin.getAccountIdString(),
                        mockedEmployee.getAccountIdString()
                );
    }

    @Test
    void itShouldDemoteAdmin(){

        underTest.addEmployee(mockedEmployee.getAccountIdString());

        underTest.demote(mockedAdmin.getAccountIdString());

        assertThat(underTest.getAdmins()).asList().containsExactly();

        assertThat(underTest.getEmployees()).asList()
                .containsExactlyInAnyOrder(
                        mockedAdmin.getAccountIdString(),
                        mockedEmployee.getAccountIdString()
                );
    }
}