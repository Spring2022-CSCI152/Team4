package com.CSCI152.team4.server.Accounts.Classes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class EmployeeAccountTest {

    EmployeeAccount underTest;

    @BeforeEach
    void setUp(){
        underTest = new EmployeeAccount(123, "employee@email", "123",
                "FName", "LName", "Employee");
    }

    @AfterEach
    void tearDown(){
        underTest = null;
    }

    @ParameterizedTest
    @MethodSource
    void isShouldNotSaveInvalidPermissions(List permissions, List expected) {
        // Given
        underTest.setPermissionsList(permissions);
        // When
        // Then
        assertThat(underTest.getPermissionsList()).asList().usingRecursiveComparison()
                .isEqualTo(expected);
    }
    static Stream<Arguments> isShouldNotSaveInvalidPermissions(){
        return Stream.of(
                Arguments.of(List.of("CR", "ER", "DR", "RAM"), List.of("CR", "ER", "DR")),
                Arguments.of(List.of("ER", "MAR"), List.of("ER")),
                Arguments.of(List.of("No", "Not Valid", "None"), List.of())
        );
    }

    @ParameterizedTest
    @MethodSource
    void itShouldEditPermissionsCorrectly(List initialPermissions, List updatedAndExpected){

        underTest.setPermissionsList(initialPermissions);

        underTest.setPermissionsList(updatedAndExpected);

        assertThat(underTest.getPermissionsList()).asList()
                .usingRecursiveComparison().isEqualTo(updatedAndExpected);
    }
    static Stream<Arguments> itShouldEditPermissionsCorrectly(){
        return Stream.of(
                Arguments.of(List.of("CR", "ER", "DR"), List.of("CR", "ER")),
                Arguments.of(List.of("ER", "EN"), List.of("ER")),
                Arguments.of(List.of("CR", "ER", "DR", "CA", "DA"), List.of())
        );
    }
}