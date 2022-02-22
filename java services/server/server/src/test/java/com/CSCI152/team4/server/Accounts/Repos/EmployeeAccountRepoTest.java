package com.CSCI152.team4.server.Accounts.Repos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(
        properties = "spring.jpa.properties.javax.persistence.validation=none"
)
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeAccountRepoTest {

    @Autowired
    EmployeeAccountRepo underTest;

    @Test
    void itShouldName() {
        // Given
        // When
        // Then
    }
}