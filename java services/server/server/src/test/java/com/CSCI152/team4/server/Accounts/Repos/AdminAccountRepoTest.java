package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Repos.AdminAccountRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/frmw",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.show-sql=false"
})
class AdminAccountRepoTest {

    @Autowired
    AdminAccountRepo underTest;

    @Autowired
    EntityManager entityManager;

    AutoCloseable mockOpener;

    @BeforeEach
    void setUp(){
        mockOpener = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown(){
        for(AdminAccount a : underTest.findAll()){
            underTest.delete(a);
        }

    }

    AdminAccount getAdminAccount(Integer businessId){
        return new AdminAccount(businessId,
                "testAdminEmail", "testPassword",
                "testFName", "testLName", "testJobTitle");
    }
    @ParameterizedTest
    @MethodSource
    void itShouldFindTopByEmailAndBusinessId(AdminAccount account) {
        // Given
        underTest.save(account);
        // When
        Optional<AdminAccount> optionalAdminAccount = underTest.findTopByEmailAndBusinessId(account.getEmail(), account.getBusinessId());
        // Then
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(account));

    }
    static Stream<Arguments> itShouldFindTopByEmailAndBusinessId(){
        return Stream.of(
                Arguments.of(new AdminAccount(123, "email1", "password1", "name1", "name2", "title1")),
                Arguments.of(new AdminAccount(124, "email2", "password2", "name3", "name4", "title2")),
                Arguments.of(new AdminAccount(125, "", "password3", "name5", "name6", "title3"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void itShouldExistsByEmailAndBusinessId(AdminAccount account) {
        // Given
        underTest.save(account);
        // When
        // Then
        assertTrue(underTest.existsByEmailAndBusinessId(account.getEmail(), account.getBusinessId()));
    }
    static Stream<Arguments> itShouldExistsByEmailAndBusinessId(){
        return Stream.of(
                Arguments.of(new AdminAccount(123, "email1", "password1", "name1", "name2", "title1")),
                Arguments.of(new AdminAccount(124, "email2", "password2", "name3", "name4", "title2")),
                Arguments.of(new AdminAccount(125, "", "password3", "name5", "name6", "title3"))
        );
    }

    @Test
    void itShouldNotSaveWithNullIdParameters(){
        AdminAccount account = getAdminAccount(null);

        Exception e = assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            underTest.save(account);
            entityManager.flush();
        });

        assertThat(e).hasMessageContaining("could not execute statement;");
    }

    @Test
    void itShouldSaveFieldChanges(){

        //Given
        AdminAccount account = getAdminAccount(127);

        underTest.save(account);
        Optional<AdminAccount> optionalAdminAccount = underTest.findById(account.getAccountId());
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
        //When
        account.setFirstName("Joe");
        account.setLastName("Mama");
        account.setJobTitle("Not Admin!");
        underTest.save(account);

        //Then
        optionalAdminAccount = Optional.empty();
        optionalAdminAccount = underTest.findById(account.getAccountId());
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
    }

    @Test
    void itShouldNotSaveIdFieldChanges(){
        AdminAccount account = getAdminAccount(127);
        AdminAccount original = getAdminAccount(127);
        original.setAccountId(account.getAccountIdString());

        underTest.save(account);
        Optional<AdminAccount> optionalAdminAccount = underTest.findById(account.getAccountId());
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
        //When
        account.setEmail("newEmail");
        account.setBusinessId(129);
        underTest.save(account);

        //Then
        optionalAdminAccount = Optional.empty();
        optionalAdminAccount = underTest.findById(account.getAccountId());
        assertThat(optionalAdminAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(original);
                });
    }
    @Test
    void itShouldDeleteEntry(){
        //Given
        AdminAccount account = getAdminAccount(127);

        underTest.save(account);

        //When
        underTest.delete(account);

        //Then
        assertFalse(underTest.existsById(account.getAccountId()));
    }
}