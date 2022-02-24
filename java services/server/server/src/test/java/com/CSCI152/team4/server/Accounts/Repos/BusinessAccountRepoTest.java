package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormatBuilder;
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

//@DataJpaTest(
//        properties = "spring.jpa.properties.javax.persistence.validation=none"
//)
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BusinessAccountRepoTest {

    @Autowired
    private BusinessAccountRepo underTest;

    @Autowired
    private EntityManager entityManager;


    AdminAccount getAdminAccount(Integer businessAccId){
        return new AdminAccount("admin", "123", "admin", "admin", "admin", businessAccId);
    }
    BusinessAccount buildBusinessAccount(Integer businessAccId){
        AdminAccount admin = getAdminAccount(businessAccId);
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", admin.getAccountId());
        ReportFormatBuilder builder = new ReportFormatBuilder(businessAccId);
        businessAccount.setReportFormat(builder.build());

        return businessAccount;
    }

    @Test
    void itShouldSaveBusinessAccount() {
        // Given
        Integer businessAccId = 127;
        BusinessAccount businessAccount = buildBusinessAccount(businessAccId);
        // When
        underTest.save(businessAccount);
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccId);

        // Then
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getBusinessId()).isEqualTo(businessAccount.getBusinessId());
                    assertThat(c.getBusinessName()).isEqualTo(businessAccount.getBusinessName());
                    assertThat(c.getAdmins()).asList().containsExactlyElementsOf(businessAccount.getAdmins());
                    assertThat(c.getEmployees()).asList().containsExactlyElementsOf((businessAccount.getEmployees()));
                });
    }

    @Test
    void itShouldThrowExceptionWhenSavedWithEmptyAdmin() {
        // Given
        Integer businessAccId = 128;
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", null);
        ReportFormatBuilder builder = new ReportFormatBuilder(businessAccId);
        businessAccount.setReportFormat(builder.build());

        // When
        // Then
        Throwable exception = assertThrows(javax.persistence.TransactionRequiredException.class,
                () -> { underTest.save(businessAccount); entityManager.flush();});

//        assertThat(exception.getMessage()).contains("Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction");
    }

    @Test
    void itShouldSaveNewAdmin() {
        //Given
        Integer businessAccId = 127;
        BusinessAccount businessAccount = buildBusinessAccount(businessAccId);
        AdminAccount admin2 = getAdminAccount(businessAccId);
        businessAccount.addAdmin(admin2.getAccountId());

        // When
        underTest.save(businessAccount);
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccId);

        // Then
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getAdmins()).asList().containsExactlyElementsOf(businessAccount.getAdmins());
                });

    }

    @Test
    void itShouldSaveNewEmployee() {
        //Given
        Integer businessAccId = 127;
        BusinessAccount businessAccount = buildBusinessAccount(businessAccId);
        EmployeeAccount employeeAccount = new EmployeeAccount("employee",
                "123", "emp", "emp", "scrub", businessAccId);
        businessAccount.addEmployee(employeeAccount.getAccountId());
        // When
        underTest.save(businessAccount);

        // Then
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccId);

        assertThat(optionalBusinessAccount).isPresent();
        if(optionalBusinessAccount.isPresent()){
            assertThat(optionalBusinessAccount.get().getEmployees())
                    .asList()
                    .containsExactlyElementsOf(List.of(employeeAccount.getAccountId()));
        }
    }


}