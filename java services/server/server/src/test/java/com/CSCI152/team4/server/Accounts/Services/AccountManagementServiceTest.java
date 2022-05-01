package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountPermissionUpdater;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountRetriever;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountStatusChanger;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountUpdater;
import com.CSCI152.team4.server.Accounts.Requests.*;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequestDAO;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequestDAO;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Util.InstanceClasses.RequestDAO;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountManagementServiceTest {

    private AccountManagementService underTest;

    @Mock
    private SecurityManager securityManager;
    @Mock
    private IAccountRetriever accounts;
    @Mock
    private IAccountUpdater updater;
    @Mock
    private IAccountPermissionUpdater permissions;
    @Mock
    private IAccountStatusChanger status;

    @Mock
    private RequestDAO requestDAO;
    @Mock
    private TargetAccountRequestDAO targetedRequest;
    @Mock
    private UpdateRequestDAO updateRequest;
    @Mock
    private UpdateOtherRequestDAO updateOtherRequest;
    @Mock
    private PermissionUpdateRequestDAO permissionUpdateRequest;

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

    EmployeeAccount getNewEmployee(){
        return new EmployeeAccount(150, "email", "password", "name1", "name2", "emp", List.of());
    }

    EmployeeAccount getEmployeeFromId(AccountId accountId){
        EmployeeAccount account = getNewEmployee();
        account.setAccountId(accountId);
        return account;
    }

    @Test
    void itShouldGetAccountInfo() {

        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId accountId = new AccountId(accountIdString, email, businessId);
        given(requestDAO.getAccountId()).willReturn(accountId);
        given(requestDAO.getToken()).willReturn(token);

        AdminAccount expected = getAdminFromId(requestDAO.getAccountId());
        doReturn(expected).when(accounts).getAccountInfo(requestDAO);

        doNothing().when(securityManager).validateToken(accountId, token);

        // When
        WorkerAccount returnable = underTest.getAccountInfo(requestDAO);
        // Then

        verify(securityManager, times(1)).validateToken(accountId, token);
        verify(accounts, times(1)).getAccountInfo(requestDAO);
        verifyNoMoreInteractions(securityManager, accounts);
        assertThat(returnable).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void itShouldGetOtherAccountInfo() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
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

        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer targetedBusinessId = 102;
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

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(accounts);
        verifyNoMoreInteractions(securityManager);
    }

    @Test
    void itShouldGetAccounts() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer targetedBusinessId = 102;
        String token = "token";
        AccountId accountId = new AccountId(accountIdString, email, businessId);
        AccountId account1 = new AccountId("account1", "account1", targetedBusinessId);
        AccountId account2 = new AccountId("account2", "account2", targetedBusinessId);
        List<WorkerAccount> accountsList
                = List.of(getAdminFromId(accountId), getAdminFromId(account1), getEmployeeFromId(account2));
        given(requestDAO.getAccountId()).willReturn(accountId);
        given(requestDAO.getToken()).willReturn(token);
        given(accounts.getAccounts(requestDAO)).willReturn(accountsList);
        Permissions expectedPermission = Permissions.ACCOUNTS_VIEW;
        doNothing().when(securityManager).validateTokenAndPermission(accountId, token, expectedPermission);


        // When
        List<WorkerAccount> actual = underTest.getAccounts(requestDAO);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(accountsList);
        verify(accounts, times(1)).getAccounts(requestDAO);
        verify(securityManager, times(1))
                .validateTokenAndPermission(accountId, token, expectedPermission);
        verifyNoMoreInteractions(securityManager, accounts);
    }

    @Test
    void itShouldUpdateInfo() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId accountId = new AccountId(accountIdString, email, businessId);
        AdminAccount expected = getAdminFromId(accountId);
        given(updateRequest.getAccountId()).willReturn(accountId);
        given(updateRequest.getToken()).willReturn(token);
        doNothing().when(securityManager).validateToken(accountId, token);
        given(updater.updateSelf(updateRequest)).willReturn(expected);
        // When
        WorkerAccount actual = underTest.updateInfo(updateRequest);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(securityManager, times(1)).validateToken(accountId, token);
        verify(updater, times(1)).updateSelf(updateRequest);
        verifyNoMoreInteractions(securityManager, updater);
    }

    /*NOTE: This test caught bug on 4-27-22,
    * showed that securityManager only validated token and NOT the permission*/
    @Test
    void itShouldUpdateOther() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", businessId);

        AdminAccount expected = getAdminFromId(target);

        given(updateOtherRequest.getAccountId()).willReturn(requester);
        given(updateOtherRequest.getTargetId()).willReturn(target);
        given(updateOtherRequest.getToken()).willReturn(token);
        given(updateOtherRequest.getBusinessId()).willReturn(businessId);

        given(updater.updateOther(updateOtherRequest)).willReturn(expected);

        Permissions expectedPermission = Permissions.ACCOUNTS_UPDATE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        // When
        WorkerAccount actual = underTest.updateOther(updateOtherRequest);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(securityManager, times(1))
                .validateTokenAndPermission(requester, token, expectedPermission);
        verify(updater, times(1)).updateOther(updateOtherRequest);
        verifyNoMoreInteractions(securityManager, updater);
    }

    @Test
    void itShouldThrowErrorOnUpdateOtherWithInvalidBusinessId(){
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer targetedBusinessId = 102;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", targetedBusinessId);
        given(updateOtherRequest.getAccountId()).willReturn(requester);
        given(updateOtherRequest.getTargetId()).willReturn(target);
        given(updateOtherRequest.getToken()).willReturn(token);

        Permissions expectedPermission = Permissions.ACCOUNTS_UPDATE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.updateOther(updateOtherRequest));
        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Different Business IDs");

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(accounts);
        verifyNoMoreInteractions(securityManager);

    }
    @Test
    void itShouldUpdateEmployeePermissions() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", businessId);

        EmployeeAccount expected = getEmployeeFromId(target);

        given(permissionUpdateRequest.getAccountId()).willReturn(requester);
        given(permissionUpdateRequest.getTargetId()).willReturn(target);
        given(permissionUpdateRequest.getToken()).willReturn(token);
        given(permissionUpdateRequest.getBusinessId()).willReturn(businessId);
        given(permissionUpdateRequest.getPermissions()).willReturn(List.of(
                Permissions.REPORT_CREATE.name(),
                Permissions.REPORT_EDIT.name(),
                Permissions.PROFILES_EDIT.name(),
                Permissions.REPORT_FORMAT.name()
        ));
        given(permissions.updatePermissions(permissionUpdateRequest)).willReturn(expected);

        Permissions expectedPermission = Permissions.PERMISSIONS_EDIT;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        // When
        WorkerAccount actual = underTest.updateEmployeePermissions(permissionUpdateRequest);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(securityManager, times(1))
                .validateTokenAndPermission(requester, token, expectedPermission);
        verify(permissions, times(1)).updatePermissions(permissionUpdateRequest);
        verifyNoMoreInteractions(securityManager, permissions);

    }

    @Test
    void itShouldThrowErrorOnSelfPermissionUpdate(){
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer invalidBusinessId = 102;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", invalidBusinessId);

        EmployeeAccount expected = getEmployeeFromId(target);

        given(permissionUpdateRequest.getAccountId()).willReturn(requester);
        given(permissionUpdateRequest.getTargetId()).willReturn(requester);
        given(permissionUpdateRequest.getToken()).willReturn(token);
        given(permissionUpdateRequest.getBusinessId()).willReturn(businessId);
        given(permissionUpdateRequest.getPermissions()).willReturn(List.of(
                Permissions.REPORT_CREATE.name(),
                Permissions.REPORT_EDIT.name(),
                Permissions.PROFILES_EDIT.name(),
                Permissions.REPORT_FORMAT.name()
        ));
        given(permissions.updatePermissions(permissionUpdateRequest)).willReturn(expected);

        Permissions expectedPermission = Permissions.PERMISSIONS_EDIT;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);

        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.updateEmployeePermissions(permissionUpdateRequest));
        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("CANNOT UPDATE OWN PERMISSIONS!");

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(permissions);
        verifyNoMoreInteractions(securityManager);
    }
    @Test
    void itShouldThrowErrorOnPermissionUpdateWithInvalidBusinessId(){
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer invalidBusinessId = 102;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", invalidBusinessId);

        EmployeeAccount expected = getEmployeeFromId(target);

        given(permissionUpdateRequest.getAccountId()).willReturn(requester);
        given(permissionUpdateRequest.getTargetId()).willReturn(target);
        given(permissionUpdateRequest.getToken()).willReturn(token);
        given(permissionUpdateRequest.getBusinessId()).willReturn(businessId);
        given(permissionUpdateRequest.getPermissions()).willReturn(List.of(
                Permissions.REPORT_CREATE.name(),
                Permissions.REPORT_EDIT.name(),
                Permissions.PROFILES_EDIT.name(),
                Permissions.REPORT_FORMAT.name()
        ));
        given(permissions.updatePermissions(permissionUpdateRequest)).willReturn(expected);

        Permissions expectedPermission = Permissions.PERMISSIONS_EDIT;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);

        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.updateEmployeePermissions(permissionUpdateRequest));
        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Different Business IDs");

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(permissions);
        verifyNoMoreInteractions(securityManager);
    }
    @Test
    void itShouldPromote() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", businessId);

        EmployeeAccount expected = getEmployeeFromId(target);

        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);
        given(targetedRequest.getBusinessId()).willReturn(businessId);

        Permissions expectedPermission = Permissions.ACCOUNTS_PROMOTE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);

        given(status.promote(targetedRequest)).willReturn(expected);
        // When
        WorkerAccount actual = underTest.promote(targetedRequest);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verify(status, times(1)).promote(targetedRequest);
        verifyNoMoreInteractions(securityManager, status);
    }

    @Test
    void itShouldThrowErrorOnPromoteWhenInvalidBusinessId(){
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer targetedBusinessId = 102;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", targetedBusinessId);
        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);

        Permissions expectedPermission = Permissions.ACCOUNTS_PROMOTE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.promote(targetedRequest));
        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Different Business IDs");

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(status);
        verifyNoMoreInteractions(securityManager);
    }

    @Test
    void itShouldDemote() {
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", businessId);

        EmployeeAccount expected = getEmployeeFromId(target);

        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);
        given(targetedRequest.getBusinessId()).willReturn(businessId);

        Permissions expectedPermission = Permissions.ACCOUNTS_DEMOTE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);

        given(status.demote(targetedRequest)).willReturn(expected);
        // When
        WorkerAccount actual = underTest.demote(targetedRequest);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verify(status, times(1)).demote(targetedRequest);
        verifyNoMoreInteractions(securityManager, status);
    }

    @Test
    void itShouldThrowErrorOnDemoteWhenInvalidBusinessId(){
        // Given
        String accountIdString = "SomeAcctId";
        String email = "someEmail";
        Integer businessId = 100;
        Integer targetedBusinessId = 102;
        String token = "token";
        AccountId requester = new AccountId(accountIdString, email, businessId);
        AccountId target = new AccountId("targetId", "targetEmail", targetedBusinessId);
        given(targetedRequest.getAccountId()).willReturn(requester);
        given(targetedRequest.getTargetId()).willReturn(target);
        given(targetedRequest.getToken()).willReturn(token);

        Permissions expectedPermission = Permissions.ACCOUNTS_DEMOTE;
        doNothing().when(securityManager).validateTokenAndPermission(requester, token, expectedPermission);
        //When
        //Then
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.demote(targetedRequest));
        assertThat(e).hasMessageContaining(HttpStatus.FORBIDDEN.name())
                .hasMessageContaining("Different Business IDs");

        verify(securityManager, times(1)).validateTokenAndPermission(requester, token, expectedPermission);
        verifyNoInteractions(status);
        verifyNoMoreInteractions(securityManager);
    }
}