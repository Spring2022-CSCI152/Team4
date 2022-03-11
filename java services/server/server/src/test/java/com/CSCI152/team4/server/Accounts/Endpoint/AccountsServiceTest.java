package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountsServiceTest {

    @Autowired
    private AccountsService underTest;

    @Mock private BusinessAccountRepo businessAccountRepo;
    @Mock private EmployeeAccountRepo employeeAccountRepo;
    @Mock private AdminAccountRepo adminAccountRepo;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountsService(adminAccountRepo,
                                        employeeAccountRepo,
                                        businessAccountRepo);
    };

    AdminAccount generateAdminAccount(Integer businessId){
        return new AdminAccount("email", "password",
                "fName", "lName",
                "jobTitle", businessId);
    }

    EmployeeAccount generateEmployeeAccountWithDefaultPermissions(Integer businessId){
        return new EmployeeAccount("email", "password",
                "fName", "lName",
                "jobTitle", businessId);
    }

    EmployeeAccount generateEmployeeWithPermissions(Integer businessId, List<String> permissions){
        return new EmployeeAccount("email", "password",
                "fName", "lName",
                "jobTitle",permissions, businessId);
    }
    //BusinessAccount Creation
    @Test
    void itShouldShouldCreateANewBusinessAccount() {
        // Given
        // Business Registration Request
            //Contains BusinessAccount Info + AdminAccount Info
        

        // When
            //underTest.registerBusinessAccount(businessAccount, adminAccount)
        // Then
            //using 'spy' or something get the business id
            //using argument captor, ensure the saved accounts are equal to current accounts
    }


    //Admin Account Creation



    //Employee Account Creation



    //Save Report Format Information



    //Account Deletion



    //Account Info Retrieval



    //Login



    //Logout




    //Set Permissions



    //Retrieve Permissions



    //Edit Account Info

}