package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    @Mock
    private TargetAccountRequest targetedRequest;

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
        AccountId accountId = new AccountId(accountIdString, email, businessId);
        given(request.getAccountId()).willReturn(accountId);
        given(request.getToken()).willReturn(token);

        AdminAccount expected = getAdminFromId(request.getAccountId());
        doReturn(expected).when(accounts).getAccountInfo(request);

        doNothing().when(securityManager).validateToken(accountId, token);

        // When
        WorkerAccount returnable = underTest.getAccountInfo(request);
        // Then

        verify(securityManager, times(1)).validateToken(accountId, token);
        verify(accounts, times(1)).getAccountInfo(request);
        verifyNoMoreInteractions(securityManager, accounts);
        assertThat(returnable).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void itShouldGetOtherAccountInfo() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = Integer.valueOf(100);
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", businessId);

        AdminAccount expected = getAdminFromId(target);

        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);
        given(targetedRequest.getBusinessId()).willReturn(businessId);

        Permissions expectedPermission = Permissions.ACCOUNTS_VIEW;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);

        doReturn(expected).when(accounts).getOtherAccountInfo(targetedRequest);

        // When
        WorkerAccount actual = underTest.getOtherAccountInfo(targetedRequest);
        // Then

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verify(accounts, times(1)).getOtherAccountInfo(targetedRequest);
        verifyNoMoreInteractions(securityManager, accounts);
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void itShouldThrowErrorOnGetOtherAccountsInfoWithDifferentBusinessIds(){
        //Given
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = Integer.valueOf(100);
        Integer targetedBusinessId = Integer.valueOf(102);
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", targetedBusinessId);
        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);

        Permissions expectedPermission = Permissions.ACCOUNTS_VIEW;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.getOtherAccountInfo(targetedRequest));

        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Different Business IDs");
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