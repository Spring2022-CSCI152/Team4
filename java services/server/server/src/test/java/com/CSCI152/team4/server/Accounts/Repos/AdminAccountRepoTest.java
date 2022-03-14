package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AdminAccountRepoTest {

    @Autowired
    AdminAccountRepo underTest;

    @Autowired
    private EntityManager entityManager;



    @Test
    void itShouldSaveNewAdminAccount() {
        // Given
        Integer businessId = 127;
        String accountId = UUID.randomUUID().toString();
        AdminAccount account = new AdminAccount();
        account.setBusinessId(businessId);
        account.setAccountId(accountId);
        // When
        underTest.save(account);

        // Then
        Optional<AdminAccount> optionalAdminAccount = underTest.findById(accountId);
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).usingRecursiveComparison().isEqualTo(account);
                });
    }

    @Test
    void itShouldThrowExceptionWhenSavedWithoutBusinessId() {
        // Given
        AdminAccount account = new AdminAccount();
        // When
        // Then
        assertThrows(Exception.class, () -> {underTest.save(account); entityManager.flush();});
    }

    @Test
    void itShouldSaveFieldChanges(){

        //Given
        Integer businessId = 127;
        AdminAccount account = new AdminAccount("admin@org.com", "password",
                "FAdmin", "LAdmin",
                "Admin", businessId);

        underTest.save(account);

        //When
        account.setEmail("diffEmail@org.com");
        account.setFirstName("Joe");
        account.setLastName("Mama");
        account.setJobTitle("Not Admin!");
        underTest.save(account);

        //Then
        Optional<AdminAccount> optionalAdminAccount = underTest.findById(account.getAccountId());
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
    }

    @Test
    void itShouldFindByEmailAndBusinessId(){
        Integer businessId = 127;
        AdminAccount account = new AdminAccount("admin@org.com", "password",
                "FAdmin", "LAdmin",
                "Admin", businessId);

        underTest.save(account);

        Optional<AdminAccount> optionalAdminAccount = underTest.findByEmailAndBusinessId(account.getEmail(), businessId);

        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
    }
}