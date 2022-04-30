package com.CSCI152.team4.server.Accounts.Services;

import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class SessionServiceTest {

    @Mock
    private AccountsRepoInterface repos;
    @Mock
    private SecurityManager securityManager;

    private SessionService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new SessionService(securityManager, repos);
    }

    @Test
    void itShouldLogin() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldLogout() {
        // Given
        // When
        // Then
    }
}