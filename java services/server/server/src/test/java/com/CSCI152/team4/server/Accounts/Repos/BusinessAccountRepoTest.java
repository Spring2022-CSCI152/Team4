package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormatBuilder;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
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



    @Test
    void itShouldSaveBusinessAccount() {
        // Given
        Integer businessAccId = 127;
        AdminAccount admin = new AdminAccount("admin", "123", "admin", "admin", "admin", businessAccId);
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", admin.getAccountId());
        ReportFormatBuilder builder = new ReportFormatBuilder(businessAccId);
        businessAccount.setReportFormat(builder.build());
        // When
        underTest.save(businessAccount);
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccId);

        // Then
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getBusinessId()).isEqualTo(businessAccount.getBusinessId());
                    assertThat(c.getBusinessName()).isEqualTo(businessAccount.getBusinessName());
                    assertThat(c.getAdmins()).asList().containsExactlyElementsOf(businessAccount.getAdmins());
                });
    }

    @Test
    void itShouldThrowExceptionWhenSavedWithEmptyAdmin() {
        // Given
        Integer businessAccId = 127;
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", null);

        // When
        // Then
        Throwable exception = assertThrows(org.springframework.transaction.TransactionSystemException.class,
                () -> {underTest.save(businessAccount);});

        assertThat(exception.getMessage()).contains("Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction");
    }

    @Test
    void itShouldSaveNewAdmin() {
        // Given
        // When
        // Then
    }

    @Test
    void itShouldSaveNewEmployee() {
        // Given
        // When
        // Then
    }


}