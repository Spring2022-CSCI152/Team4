package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessRegistrationRequest;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountsServiceTest {

    @Autowired
    private AccountsService underTest;

    @Mock private BusinessAccountRepo businessAccountRepo;
    @Mock private EmployeeAccountRepo employeeAccountRepo;
    @Mock private AdminAccountRepo adminAccountRepo;

    @Captor
    ArgumentCaptor<BusinessAccount> businessAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<AdminAccount> adminAccountArgumentCaptor;

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
    AdminAccount generateAdminAccountWithoutBusinessId(){
        return new AdminAccount("email", "password",
                "fName", "lName",
                "jobTitle");
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
        String businessName = "Business";
        AdminAccount adminAccount = generateAdminAccountWithoutBusinessId();
        BusinessRegistrationRequest request =
                new BusinessRegistrationRequest(businessName,
                        adminAccount.getEmail(), adminAccount.getPassword(), ,
                        adminAccount.getFirstName(), adminAccount.getLastName(),
                        adminAccount.getJobTitle());


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