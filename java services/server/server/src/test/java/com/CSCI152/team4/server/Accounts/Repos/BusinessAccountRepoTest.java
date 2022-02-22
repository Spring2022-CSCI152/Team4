package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(
        properties = "spring.jpa.properties.javax.persistence.validation=none"
)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessAccountRepoTest {

    @Autowired
    private BusinessAccountRepo underTest;



    @Test
    void itShouldSaveBusinessAccount() {
        // Given
        int businessAccId = 127;
        AdminAccount admin = new AdminAccount("admin", "123", "admin", "admin", "admin", businessAccId);
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", admin.getAccountId());
        // When
        underTest.save((businessAccount));
        // Then


    }

    @Test
    void itShouldThrowExceptionWhenSavedWithEmptyAdmin() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldSaveNewAdmin() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldSaveNewEmployee() {
        // Given
        // When
        // Then
    }


}