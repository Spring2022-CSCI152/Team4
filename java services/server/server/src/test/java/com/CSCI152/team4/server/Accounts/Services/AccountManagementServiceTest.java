package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Utils.AccountPermissionUpdater;
import com.CSCI152.team4.server.Accounts.Utils.AccountRetriever;
import com.CSCI152.team4.server.Accounts.Utils.AccountStatusChanger;
import com.CSCI152.team4.server.Accounts.Utils.AccountUpdater;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountManagementServiceTest {

    AccountManagementService underTest;

    @Mock
    private SecurityUtil securityManager;
    @Mock
    private AccountRetriever accounts;
    @Mock
    private AccountUpdater updater;
    @Mock
    private AccountPermissionUpdater permissions;
    @Mock
    private AccountStatusChanger status;

    @Mock
    private Request request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        underTest =
                new AccountManagementService(
                        securityManager,
                        accounts,
                        updater,
                        permissions,
                        status);
    }

    AdminAccount getNewAdmin(){
        return new AdminAccount(150, "email", "password", "name1", "name2", "admin");
    }

    AdminAccount getAdminFromId(AccountId accountId){
        AdminAccount account = getNewAdmin();
        account.setAccountId(accountId);
        return account;
    }
    @Test
    void itShouldGetAccountInfo() {
        /*Behavior:
        * Takes a "Request" Parameter then does the following
        * 1. Verify permissions using AccountId accountId and String token
        * 2. Call and Return accounts.getAccountInfo(//)
        *
        * Testing:
        * Ensure securityManager.validateToken(AccountId accountId, String token) gets called once
        * Ensure accounts.getAccountInfo(AccountId accountId)*/
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = Integer.valueOf(100);
        String token = "token";

        given(request.getAccountId()).willReturn(new AccountId(accountIdString, email, businessId));
        AdminAccount expected = getAdminFromId(request.getAccountId());
        doReturn(expected).when(accounts).getAccountInfo(request);

        given(request.getToken()).willReturn(token);

        doNothing().when(securityManager).validateToken(any(), any());

        // When
        WorkerAccount returnable = underTest.getAccountInfo(request);
        // Then

        verify(securityManager, times(1)).validateToken(any(), any());
        verify(accounts, times(1)).getAccountInfo(request);
        verifyNoMoreInteractions(securityManager, accounts);
        assertThat(returnable).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void itShouldGetOtherAccountInfo() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldGetAccounts() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldUpdateInfo() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldUpdateOther() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldUpdateEmployeePermissions() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldPromote() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldDemote() {
        // Given
        // When
        // Then
    }
}