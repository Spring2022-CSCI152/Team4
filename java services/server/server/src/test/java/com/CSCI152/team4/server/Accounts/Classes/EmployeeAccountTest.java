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

@SuppressWarnings("unchecked")
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @ParameterizedTest
    @MethodSource
    void isShouldNotSaveInvalidPermissions(@SuppressWarnings("rawtypes") List permissions, @SuppressWarnings("rawtypes") List expected) {
        // Given
        underTest.setPermissionsList(permissions);
        // When
        // Then
        assertThat(underTest.getPermissionsList()).asList().usingRecursiveComparison()
                .isEqualTo(expected);
    }
    static Stream<Arguments> isShouldNotSaveInvalidPermissions(){
        return Stream.of(
                Arguments.of(List.of("REPORT_CREATE", "REPORT_EDIT", "REPORT_DELETE", "RAM"),
                        List.of("REPORT_CREATE", "REPORT_EDIT", "REPORT_DELETE")),
                Arguments.of(List.of("REPORT_EDIT", "MAR"), List.of("REPORT_EDIT")),
                Arguments.of(List.of("No", "Not Valid", "None"), List.of())
        );
    }

    @SuppressWarnings("rawtypes")
    @ParameterizedTest
    @MethodSource
    void itShouldEditPermissionsCorrectly(@SuppressWarnings("rawtypes") List initialPermissions, @SuppressWarnings("rawtypes") List updatedAndExpected){

        //noinspection unchecked
        underTest.setPermissionsList(initialPermissions);

        underTest.setPermissionsList(updatedAndExpected);

        assertThat(underTest.getPermissionsList()).asList()
                .usingRecursiveComparison().isEqualTo(updatedAndExpected);
    }
    static Stream<Arguments> itShouldEditPermissionsCorrectly(){
        return Stream.of(
                Arguments.of(List.of("REPORT_CREATE", "REPORT_EDIT", "REPORT_DELETE"),
                        List.of("REPORT_CREATE", "REPORT_EDIT")),
                Arguments.of(List.of("REPORT_EDIT", "NOTIFICATIONS_EDIT"), List.of("REPORT_EDIT")),
                Arguments.of(List.of("REPORT_CREATE", "REPORT_EDIT", "REPORT_DELETE", "ATTACHMENTS_CREATE", "ATTACHMENTS_DELETE"),
                        List.of())
        );
    }
}