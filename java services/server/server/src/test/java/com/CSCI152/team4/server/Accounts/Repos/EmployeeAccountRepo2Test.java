package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeAccountRepo2Test {

    @Autowired
    EmployeeAccountRepo2 underTest;

    @Autowired
    private EntityManager entityManager;

    @Test
    void itShouldSaveEmployeeAccount() {
        // Given
        EmployeeAccount accountWNullPermission = new EmployeeAccount("email", "pass",
                "fName", "lName", "title", null,
                127);

        // When
        underTest.save(accountWNullPermission);

        // Then
        Optional<EmployeeAccount> optionalEmployeeAccount =
                underTest.findById(accountWNullPermission.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //"permissions" is a transient hashmap
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp")
                            .isEqualTo(accountWNullPermission);
                });
    }

    @Test
    void itShouldThrowExceptionWhenNoBusinessId() {
        // Given
        EmployeeAccount emptyAccount = new EmployeeAccount();
        // When
        // Then
        assertThrows(Exception.class, () -> {underTest.save(emptyAccount); entityManager.flush();});
    }

    @Test
    void itShouldSaveFieldChanges() {
        // Given
        EmployeeAccount accountWNullPermission = new EmployeeAccount("email", "pass",
                "fName", "lName", "title", null,
                127);
        underTest.save(accountWNullPermission);

        // When
        accountWNullPermission.setEmail("newEmail@org.com");
        accountWNullPermission.setFirstName("NewName");
        underTest.save(accountWNullPermission);
        // Then
        Optional<EmployeeAccount> optionalEmployeeAccount =
                underTest.findById(accountWNullPermission.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("permissions", "timestamp")
                            .isEqualTo(accountWNullPermission);
                });
    }

    @Test
    void itShouldSavePermissionChanges() {
        // Given
        EmployeeAccount accountWNullPermission = new EmployeeAccount("email", "pass",
                "fName", "lName", "title", null,
                127);
        underTest.save(accountWNullPermission);


        // When
        List<String> perms = List.of("CR", "ER", "DR", "CU", "EU", "DU", "CA");
        accountWNullPermission.setPermissions_list(perms);
        underTest.save(accountWNullPermission);
        // Then
        Optional<EmployeeAccount> optionalEmployeeAccount =
                underTest.findById(accountWNullPermission.getAccountId());
        assertThat(optionalEmployeeAccount).hasValueSatisfying(c -> {
           assertThat(c.getPermissions_list()).usingRecursiveComparison().isEqualTo(perms);
        });
    }

    @Test
    void itShouldFindByEmailAndBusinessId(){
        Integer businessId = 127;
        EmployeeAccount accountWNullPermission = new EmployeeAccount("email", "pass",
                "fName", "lName", "title", null,
                businessId);

        underTest.save(accountWNullPermission);

        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findFirstByEmailAndBusinessId(accountWNullPermission.getEmail(), businessId);

        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(accountWNullPermission);
                });
    }
}