package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class AccountRetrieverTest {

    @Mock
    private AccountsRepoInterface repos;

    @Mock
    private WorkerAccount account;

    @Mock
    private EmployeeAccount employeeAccount;

    @Mock
    private TargetAccountRequest request;

    private AccountRetriever underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new AccountRetriever(repos);
    }

    @Test
    void itShouldGetAccountInfo() {
//        if(repos.businessExists(request.getBusinessId())){
//            WorkerAccount returnable
//                    = repos.getAccountByEmailAndBusinessId(request.getAccountEmail(),
//                    request.getBusinessId());
//            /*This does not require an Admin, therefore do not call getReturnableOnAdminExists*/
//            /*Do not return password!*/
//            returnable.setPassword(null);
//
//            return returnable;
//        }
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account Not Found!");
        // Given
        Integer businessId = 100;
        String email = "email";
        given(request.getAccountEmail()).willReturn(email);
        given(request.getBusinessId()).willReturn(businessId);
        given(repos.businessExists(businessId)).willReturn(true);
        given(repos.getAccountByEmailAndBusinessId(email, businessId)).willReturn(account);
        doNothing().when(account).setPassword(null);
        // When
        WorkerAccount actual = underTest.getAccountInfo(request);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(account);
        verify(repos, times(1)).businessExists(businessId);
        verify(repos, times(1)).getAccountByEmailAndBusinessId(email, businessId);
        verify(account, times(1)).setPassword(null);
        verifyNoMoreInteractions(repos, account);
    }

    @Test
    void itShouldThrowErrorOnBusinessNotFound(){
        //Given
        Integer businessId = 100;
        given(request.getBusinessId()).willReturn(businessId);
        given(repos.businessExists(any())).willReturn(false);

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getAccountInfo(request));

        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Account Not Found!");

        verify(request, times(1)).getBusinessId();
        verify(repos, times(1)).businessExists(businessId);
        verifyNoMoreInteractions(repos, request);
    }
    @Test
    void itShouldGetOtherAccountInfo() {
        // Given
        Integer businessId = 100;
        String email = "email";
        AccountId targetId = new AccountId("", email, businessId);

        given(request.getBusinessId()).willReturn(businessId);
        given(repos.businessExists(businessId)).willReturn(true);
        given(repos.getAccountByEmailAndBusinessId(email,businessId)).willReturn(account);
        given(request.getTargetId()).willReturn(targetId);

        doNothing().when(account).setPassword(null);
        // When
        WorkerAccount actual = underTest.getOtherAccountInfo(request);
        // Then
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(account);

        verify(repos, times(1)).businessExists(businessId);
        verify(repos, times(1)).getAccountByEmailAndBusinessId(email, businessId);
        verify(account, times(1)).setPassword(null);
        verify(request, times(2)).getTargetId();
        verify(request, times(1)).getBusinessId();
        verifyNoMoreInteractions(repos, account);
    }

    @Test
    void itShouldThrowOnBusinessNotFoundOnGetOtherAccountInfo(){
        Integer businessId = 100;
        given(request.getBusinessId()).willReturn(businessId);
        given(repos.businessExists(any())).willReturn(false);

        // When
        Exception e = assertThrows(ResponseStatusException.class,
                () -> underTest.getOtherAccountInfo(request));

        // Then
        assertThat(e)
                .hasMessageContaining(HttpStatus.NOT_FOUND.name())
                .hasMessageContaining("Business Account Not Found!");

        verify(request, times(1)).getBusinessId();
        verify(repos, times(1)).businessExists(businessId);
        verifyNoMoreInteractions(repos, request);
    }
    
    @Test
    void itShouldGetAccounts() {
        // Given
        // When
        // Then
    }
}