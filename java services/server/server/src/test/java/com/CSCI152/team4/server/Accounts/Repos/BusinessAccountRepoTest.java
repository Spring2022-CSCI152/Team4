package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
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
        addReportFormatAndProfileFormat(businessAccount);

        return businessAccount;
    }

    void addReportFormatAndProfileFormat(BusinessAccount acc){

        ReportFormatBuilder reportFormatBuilder = new ReportFormatBuilder();
        acc.setReportFormat(reportFormatBuilder.build());
        //ProfileFormatBuilder profileFormatBuilder = new ProfileFormatBuilder(acc.getBusinessId());
        //acc.setProfileFormat(profileFormatBuilder.build());
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
                    assertThat(c).usingRecursiveComparison().isEqualTo(businessAccount);
                });
    }

    @Test
    void itShouldThrowExceptionWhenSavedWithEmptyAdmin() {
        // Given
        Integer businessAccId = 128;
        BusinessAccount businessAccount = new BusinessAccount(businessAccId, "Business", null);
        addReportFormatAndProfileFormat(businessAccount);

        // When
        // Then
        assertThrows(javax.persistence.TransactionRequiredException.class,
                () -> { underTest.save(businessAccount); entityManager.flush();});

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

    @Test
    void isShouldSaveNewReportFormat(){
        //Given
        Integer businessAccId = 130;
        BusinessAccount businessAccount = buildBusinessAccount(businessAccId);

        ReportFormat oldFormat = businessAccount.getReportFormat();
        underTest.save(businessAccount);

        //When
        ReportFormat newFormat = new ReportFormatBuilder()
                .enableReportId()
                .enableAuthor()
                .enableBox1()
                .enableBox2()
                .nameBox1("Source of Activity")
                .nameBox2("Description")
                .build();
        businessAccount.setReportFormat(newFormat);
        underTest.save(businessAccount);

        //Then
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccount.getBusinessId());

        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getReportFormat()).usingRecursiveComparison().isEqualTo(newFormat);
                });

    }

    @Test
    void isShouldSaveNewProfileFormat(){


    }

}