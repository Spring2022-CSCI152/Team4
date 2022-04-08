package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Repos.EmployeeAccountRepo;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/frmw",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.show-sql=false"
})
class EmployeeAccountRepoTest {

    @Autowired
    EmployeeAccountRepo underTest;

    @Autowired
    private EntityManager entityManager;

    AutoCloseable mockOpener;

    @BeforeEach
    void setUp(){
        mockOpener = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown(){
        for(EmployeeAccount a : underTest.findAll()){
            underTest.delete(a);
        }
    }

    EmployeeAccount getEmployeeAccount(Integer businessId){
        return new EmployeeAccount(businessId,
                "testEmployeeEmail", "testPassword",
                "testFName", "testLName", "testJobTitle");
    }

    /*Assuming Correct Behavior of 'save()' the following tests verify the
    * customized functions in the respective interfaces
    * */

    @ParameterizedTest
    @MethodSource
    void itShouldFindTopByEmailAndBusinessId(EmployeeAccount account) {
        // Given
        underTest.save(account);
        // When
        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findTopByEmailAndBusinessId(account.getEmail(), account.getBusinessId());
        // Then
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison().ignoringFields("timestamp").isEqualTo(account));
    }
    static Stream<Arguments> itShouldFindTopByEmailAndBusinessId(){
        return Stream.of(
                Arguments.of(new EmployeeAccount(123, "email1", "password1", "name1", "name2", "title1")),
                Arguments.of(new EmployeeAccount(124, "email2", "password2", "name3", "name4", "title2")),
                Arguments.of(new EmployeeAccount(125, "", "password3", "name5", "name6", "title3"))
        );
    }

    @ParameterizedTest
    @MethodSource
    void itShouldExistsByEmailAndBusinessId(EmployeeAccount account) {
        // Given
        underTest.save(account);
        // When
        // Then
        assertTrue(underTest.existsByEmailAndBusinessId(account.getEmail(), account.getBusinessId()));
    }
    static Stream<Arguments> itShouldExistsByEmailAndBusinessId(){
        return Stream.of(
                Arguments.of(new EmployeeAccount(123, "email1", "password1", "name1", "name2", "title1")),
                Arguments.of(new EmployeeAccount(124, "email2", "password2", "name3", "name4", "title2")),
                Arguments.of(new EmployeeAccount(125, "", "password3", "name5", "name6", "title3"))
        );
    }

    @Test
    void itShouldNotSaveWithNullIdParameters(){
        EmployeeAccount account = getEmployeeAccount(null);


        Exception e = assertThrows(org.springframework.dao.DataIntegrityViolationException.class, () -> {
            underTest.save(account);
//            entityManager.flush();
        });
        assertThat(e).hasMessageContaining("could not execute statement");



    }

    @Test
    void itShouldSaveFieldChanges(){

        //Given
        EmployeeAccount account = getEmployeeAccount(127);

        underTest.save(account);
        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
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
        optionalEmployeeAccount = Optional.empty();
        optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
    }

    @Test
    void itShouldNotSaveIdFieldChanges(){
        EmployeeAccount account = getEmployeeAccount(127);
        EmployeeAccount original = getEmployeeAccount(127);
        original.setAccountId(account.getAccountIdString());

        underTest.save(account);
        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
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
        optionalEmployeeAccount = Optional.empty();
        optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(original);
                });
    }


    @ParameterizedTest
    @MethodSource
    void itShouldSavePermissionsList(List<String> permissions){

        EmployeeAccount account = getEmployeeAccount(127);

        underTest.save(account);
        Optional<EmployeeAccount> optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });
        //When
        account.setPermissionsList(permissions);
        underTest.save(account);

        //Then
        optionalEmployeeAccount = Optional.empty();
        optionalEmployeeAccount = underTest.findById(account.getAccountId());
        assertThat(optionalEmployeeAccount).isPresent()
                .hasValueSatisfying(c -> {
                    //Timestamp ignored for Precision Issues on comparison
                    assertThat(c).usingRecursiveComparison()
                            .ignoringFields("timestamp").isEqualTo(account);
                });

    }
    static Stream<Arguments> itShouldSavePermissionsList(){
        return Stream.of(
                Arguments.of(List.of("CR", "ER", "DR", "CU", "EU", "DU", "CA", "DA", "CI", "EI", "CP", "EP", "DP", "EN")),
                Arguments.of(List.of("ER")),
                Arguments.of(List.of())
        );
    }

}
