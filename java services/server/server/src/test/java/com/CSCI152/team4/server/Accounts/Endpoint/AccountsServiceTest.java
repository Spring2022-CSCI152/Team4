package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessRegistrationRequest;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Accounts.Repos.EmployeeAccountRepo;
import com.CSCI152.team4.server.Util.AccountAuthenticator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class AccountsServiceTest {

    @Autowired
    private AccountsService underTest;

    @Mock private BusinessAccountRepo businessAccountRepo;
    @Mock private EmployeeAccountRepo employeeAccountRepo;
    @Mock private AdminAccountRepo adminAccountRepo;
    @Mock private BusinessAccount businessAccount;
    @Mock private AdminAccount adminAccount;
    @Mock private AccountAuthenticator accountAuthenticator;
    @Mock private EmployeeAccount employeeAccount;

    @Captor
    ArgumentCaptor<BusinessAccount> businessAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<AdminAccount> adminAccountArgumentCaptor;


    BusinessRegistrationRequest getRegistrationRequest(){
        return new BusinessRegistrationRequest("Business",
                adminAccount.getEmail(), adminAccount.getPassword(),
                adminAccount.getFirstName(), adminAccount.getLastName(),
                adminAccount.getJobTitle());
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountsService(adminAccountRepo,
                                        employeeAccountRepo,
                                        businessAccountRepo);
    }


    //BusinessAccount Creation
    @Test
    @DisplayName("A new Business Account gets generated along side an attached Admin Account")
    void itShouldShouldCreateANewBusinessAccountWithCorrespondingAdminAccount() {
        // Given
        Integer businessId = 127;
        given(businessAccountRepo.save(any())).willReturn(businessAccount);
        given(businessAccount.getBusinessId()).willReturn(businessId);


        //when
        underTest.registerBusinessAccount(this.getRegistrationRequest());

        //then
        verify(businessAccountRepo).save(businessAccountArgumentCaptor.capture());
        verify(adminAccountRepo).save(adminAccountArgumentCaptor.capture());

        /*
        * Given the registration function, a business account and an admin account
        * are created sequentially, however due to constraints in the database, the
        * business account REQUIRES at minimum a single admin account connected to it
        *
        * To test for this, ensure the captured business account contains ONLY the account id
        * for the admin account
        * */
        assertThat(businessAccountArgumentCaptor.getValue().getAdmins())
                .asList().containsOnly(adminAccountArgumentCaptor.getValue().getAccountId());

        /*
        * Given that the repo's are mocked, they will not return the generated ID values. Therefore, when
        * the admin account gets assigned the Business ID from the saved entity, we simulate this with
        * the mock data.
        *
        * To test proper execution, we verify the admin account has the above defined business ID
        * */
        assertThat(adminAccountArgumentCaptor.getValue().getBusinessId()).isEqualTo(businessId);

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