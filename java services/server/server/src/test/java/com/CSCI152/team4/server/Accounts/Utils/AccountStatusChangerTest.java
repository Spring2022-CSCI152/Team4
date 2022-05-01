package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountTransposer;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountStatusChangerTest {

    @Mock
    private AccountsRepoInterface accounts;

    @Mock
    private IAccountTransposer transposer;

    @Mock
    AdminAccount accountToDemote;

    @Mock
    EmployeeAccount accountToPromote;

    @Mock
    BusinessAccount businessAccount;

    @Mock
    private TargetAccountRequest request;

    @Captor
    ArgumentCaptor<AdminAccount> adminAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<EmployeeAccount> employeeAccountArgumentCaptor;
    @Captor
    ArgumentCaptor<AccountId> accountIdArgumentCaptor;

    AccountStatusChanger underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountStatusChanger(accounts, transposer);
    }

    @Test
    void itShouldPromote()
            throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
//        BusinessAccount businessAccount = accounts.getBusinessIfExists(request.getBusinessId());
//        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
//                .equals(BusinessAccount.employeeAccountType)){
//
//            AdminAccount ret = promoteToAdmin(businessAccount, request.getTargetId());
//            ret.setPassword(null);
//            return ret;
//        }
//        try{
//            /* Transpose EmployeeAccount to AdminAccount*/
//            AdminAccount newAdmin = (AdminAccount) transposer.transposeTo(AdminAccount.class, accounts.getEmployeeIfExists(accountToPromote));
//            /*Save to AdminTable*/
//            accounts.saveAdminAccount(newAdmin);
//            /*Remove from EmployeeTable*/
//            accounts.removeEmployeeAccount(accountToPromote);
//            /* Return database successfully swapped entries*/
//            if(accounts.adminExists(accountToPromote) && !accounts.employeeExists(accountToPromote)){
//                return accounts.getAdminIfExists(accountToPromote);
//            }
        // Given
        Integer businessId = 100;
        String targetIdString = "idString";
        String targetEmail = "email";
        AccountId targetId = new AccountId(targetIdString, targetEmail, businessId);

        given(request.getBusinessId()).willReturn(businessId);
        given(request.getTargetId()).willReturn(targetId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(businessAccount);
        given(businessAccount.getAccountType(targetIdString))
                .willReturn(BusinessAccount.employeeAccountType);
        doNothing().when(businessAccount).promote(targetIdString);
        given(accounts.saveBusinessAccount(businessAccount)).willReturn(businessAccount);
        given(transposer.transposeTo(AdminAccount.class, accountToPromote))
                .willReturn(accountToDemote);
        given(accounts.saveAdminAccount(accountToDemote)).willReturn(accountToDemote);
        given(accounts.removeEmployeeAccount(targetId)).willReturn(true);
        given(accounts.adminExists(targetId)).willReturn(true);
        given(accounts.employeeExists(targetId)).willReturn(false);
        given(accounts.getAdminIfExists(targetId)).willReturn(accountToDemote);
        doNothing().when(accountToDemote).setPassword(null);
        given(accounts.getEmployeeIfExists(targetId)).willReturn(accountToPromote);
        // When

        WorkerAccount actual = underTest.promote(request);
        // Then
        assertThat(actual).usingRecursiveComparison().isEqualTo(accountToDemote);
        verify(accounts).saveAdminAccount(adminAccountArgumentCaptor.capture());
        verify(accounts).removeEmployeeAccount(accountIdArgumentCaptor.capture());
        verify(accounts, times(1)).saveBusinessAccount(businessAccount);
        verify(accounts, times(1)).saveAdminAccount(accountToDemote);
        verify(accounts, times(1)).removeEmployeeAccount(targetId);
        assertThat(adminAccountArgumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(accountToDemote);
        assertThat(accountIdArgumentCaptor.getValue()).usingRecursiveComparison()
                .isEqualTo(targetId);
    }

    @Test
    void itShouldThrowOnNoBusinessFoundOnPromote() throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException{
        //Given
        Integer businessId = 100;
        given(request.getBusinessId()).willReturn(businessId);
        given(accounts.getBusinessIfExists(businessId)).willReturn(null);

        //When
        Exception e = assertThrows(ResponseStatusException.class, () -> underTest.promote(request));

        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Account not found!");
    }

    @Test
    void itShouldThrowOnTransposerThrowDuringPromote() throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException{

    }

    @Test
    void itShouldDemote() throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        // Given
        // When
        // Then
    }


    @Test
    void itShouldThrowOnNoBusinessFoundOnDemote() throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException{

    }

    @Test
    void itShouldThrowOnTransposerThrowDuringDemote() throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException{

    }

}