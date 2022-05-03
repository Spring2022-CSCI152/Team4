package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.PermissionUpdateRequestDAO;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountPermissionUpdaterTest {

    @Mock
    private AccountsRepoInterface accounts;

    @Mock
    private PermissionUpdateRequestDAO request;

    @Mock
    private EmployeeAccount account;

    @Captor
    ArgumentCaptor<EmployeeAccount> employeeAccountArgumentCaptor;

    AccountPermissionUpdater underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountPermissionUpdater(accounts);
    }

    @Test
    void itShouldUpdatePermissions() {

        // Given
        AccountId targetId = new AccountId();
        given(request.getTargetId()).willReturn(targetId);
        given(accounts.getEmployeeIfExists(targetId)).willReturn(account);
        given(request.getPermissions()).willReturn(List.of());
        doNothing().when(account).setPermissionsList(any());
        given(accounts.saveEmployeeAccount(account)).willReturn(account);
        doNothing().when(account).setPassword(null);

        // When
        WorkerAccount actual = underTest.updatePermissions(request);
        // Then

        assertThat(actual).usingRecursiveComparison().isEqualTo(account);

        verify(accounts).saveEmployeeAccount(employeeAccountArgumentCaptor.capture());

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(employeeAccountArgumentCaptor.getValue());

        verify(account, times(1)).setPermissionsList(any());
        verify(account, times(1)).setPassword(null);


    }
}