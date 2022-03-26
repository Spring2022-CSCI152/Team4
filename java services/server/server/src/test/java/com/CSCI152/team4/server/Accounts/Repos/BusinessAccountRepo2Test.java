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
class BusinessAccountRepo2Test {

    @Autowired
    private BusinessAccountRepo2 underTest;

    @Autowired
    private EntityManager entityManager;


    AdminAccount getAdminAccount(Integer businessAccId){
        return new AdminAccount("admin", "123", "admin", "admin", "admin", businessAccId);
    }

    AdminAccount getBlankAdminAccount(){
        return new AdminAccount("admin", "123", "admin", "admin", "admin");
    }
    BusinessAccount buildEmptyBusinessAccount(){    return new BusinessAccount("Business"); }

    BusinessAccount buildBusinessAccountWithAdmin(){
        BusinessAccount acct = new BusinessAccount("Business");
        AdminAccount admin = getBlankAdminAccount();
        acct.addAdmin(admin.getAccountId());
        return acct;
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
        BusinessAccount businessAccount = buildEmptyBusinessAccount();
        businessAccount.addAdmin(getBlankAdminAccount().getAccountId());
        // When
        businessAccount.setBusinessId(underTest.save(businessAccount).getBusinessId());


        // Then
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessAccount.getBusinessId());
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
        BusinessAccount businessAccount = buildBusinessAccountWithAdmin();
        AdminAccount admin2 = getBlankAdminAccount();
        businessAccount.addAdmin(admin2.getAccountId());

        // When
        Integer businessAccId = underTest.save(businessAccount).getBusinessId();
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
        BusinessAccount businessAccount = buildBusinessAccountWithAdmin();
        Integer businessAccId = underTest.save(businessAccount).getBusinessId();

        // When
        businessAccount.setBusinessId(businessAccId);
        EmployeeAccount employeeAccount = new EmployeeAccount("employee",
                "123", "emp", "emp", "scrub", businessAccId);
        businessAccount.addEmployee(employeeAccount.getAccountId());
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
        BusinessAccount businessAccount = buildBusinessAccountWithAdmin();

        ReportFormat oldFormat = businessAccount.getReportFormat();
        Integer businessId = underTest.save(businessAccount).getBusinessId();
        businessAccount.setBusinessId(businessId);

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
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(businessId);

        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c.getReportFormat()).usingRecursiveComparison().isEqualTo(newFormat);
                });

    }

    @Test
    void isShouldSaveNewProfileFormat(){


    }

}