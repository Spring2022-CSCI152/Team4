package com.CSCI152.team4.server.AccountsReformat.Repos;

import com.CSCI152.team4.server.AccountsReformat.AccountClasses.EmployeeAccount;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.AutoCloseableSoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        underTest = null;
    }

    EmployeeAccount getEmployeeAccount(Integer businessId){
        return new EmployeeAccount(businessId,
                "testEmployeeEmail", "testPassword",
                "testFName", "testLName", "testJobTitle");
    }


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

    @Test
    void itShouldExistsByEmailAndBusinessId() {
        // Given
        // When
        // Then
    }
}