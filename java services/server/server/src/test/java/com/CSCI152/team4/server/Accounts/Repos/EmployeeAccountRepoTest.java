package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeAccountRepoTest {

    @Autowired
    EmployeeAccountRepo underTest;

    @Test
    void itShouldSaveEmployeeAccount() {
        // Given
        EmployeeAccount account = new EmployeeAccount("email", "pass",
                "fName", "lName", "title", null,
                127);
        // When
        underTest.save(account);
        // Then
        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).usingRecursiveComparison().isEqualTo(account);
                });
    }
}