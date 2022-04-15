package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Repos.BusinessAccountRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.TransactionSystemException;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ExtendWith(SpringExtension.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/frmw",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.show-sql=false"
})
class BusinessAccountRepoTest {

    @Autowired
    BusinessAccountRepo underTest;

    @Autowired
    EntityManager entityManager;

    @Mock
    AdminAccount mockAdmin;

    @Mock
    EmployeeAccount mockEmployee;

    AutoCloseable mockOpener;

    @BeforeEach
    void setUp(){
        mockOpener = MockitoAnnotations.openMocks(this);
        doReturn("adminId").when(mockAdmin).getAccountIdString();
        doReturn("employeeId").when(mockEmployee).getAccountIdString();
    }

    @AfterEach
    void tearDown(){
        for(BusinessAccount a : underTest.findAll()){
            underTest.delete(a);
        }
    }

    BusinessAccount getBusinessAccount(){
        return new BusinessAccount("TestBusiness", mockAdmin.getAccountIdString());
    }

    @Test
    void itShouldSaveNewAccount(){

        //Given
        BusinessAccount account = getBusinessAccount();
        //When
        account.setBusinessId(underTest.save(account).getBusinessId());

        //Then
        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
        .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
    }


    @Test
    void itShouldThrowExceptionOnNoAdmin(){
        BusinessAccount account = getBusinessAccount();
        account.removeAdmin(mockAdmin.getAccountIdString());


        Exception e = assertThrows(TransactionSystemException.class,
                () -> {
                    underTest.save(account);
//                    entityManager.flush();
                });
        assertTrue(e.getCause().getCause() instanceof ConstraintViolationException);
    }

    @Test
    void itShouldAddAdmin(){
        //Given
        BusinessAccount account = getBusinessAccount();
        account.setBusinessId(underTest.save(account).getBusinessId());

        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
        //When
        account.addAdmin(mockAdmin.getAccountIdString() + "2");
        underTest.save(account);

        //Then
        optionalBusinessAccount = Optional.empty();
        optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
    }

    @Test
    void itShouldAddEmployee(){
        //Given
        BusinessAccount account = getBusinessAccount();
        account.setBusinessId(underTest.save(account).getBusinessId());

        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
        //When
        account.addAdmin(mockEmployee.getAccountIdString() + "2");
        underTest.save(account);

        //Then
        optionalBusinessAccount = Optional.empty();
        optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient

    }

    @Test
    void itShouldRemoveAdmin(){
        //Given
        BusinessAccount account = getBusinessAccount();
        account.addAdmin(mockAdmin.getAccountIdString() + "2");

        account.setBusinessId(underTest.save(account).getBusinessId());

        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
        //When
        account.removeAdmin(mockAdmin.getAccountIdString());
        underTest.save(account);

        //Then
        optionalBusinessAccount = Optional.empty();
        optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient

    }

    @Test
    void itShouldRemoveEmployee(){
        //Given
        BusinessAccount account = getBusinessAccount();
        account.addEmployee(mockEmployee.getAccountIdString() + "2");

        account.setBusinessId(underTest.save(account).getBusinessId());

        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
        //When
        account.removeEmployee(mockEmployee.getAccountIdString());
        underTest.save(account);

        //Then
        optionalBusinessAccount = Optional.empty();
        optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient

    }

    @Test
    void itShouldPromoteAndDemote(){
        //Given
        BusinessAccount account = getBusinessAccount();
        account.addAdmin(mockAdmin.getAccountIdString() + "2");
        account.addEmployee(mockEmployee.getAccountIdString() + "2");

        account.setBusinessId(underTest.save(account).getBusinessId());

        Optional<BusinessAccount> optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient
        //When
        account.removeAdmin(mockEmployee.getAccountIdString());
        underTest.save(account);

        //Then
        optionalBusinessAccount = Optional.empty();
        optionalBusinessAccount = underTest.findById(account.getBusinessId());
        assertThat(optionalBusinessAccount).isPresent()
                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
                        .ignoringFields("accountMapper").isEqualTo(account)); //Account Mapper is transient

    }


    /*Remaining Tests are for Report Format and Profile Format adjustments*/
//    @Test
//    void itShouldSaveNonBasicReportFormat(){
//        //Given
//        BusinessAccount account = getBusinessAccount();
//        account.setReportFormat(new ReportFormatBuilder()
//                .enableReportId()
//                .enableAttachments()
//                .enableAuthor()
//                .enableChangeLog()
//                .enableProfiles()
//                .enableType().build());
//        //When
//        account.setBusinessId(underTest.save(account).getBusinessId());
//
//        Optional<BusinessAccount> optionalBusinessAccount =
//                underTest.findById(account.getBusinessId());
//
//        assertThat(optionalBusinessAccount).isPresent()
//                .hasValueSatisfying(c -> assertThat(c).usingRecursiveComparison()
//                        .ignoringFields("accountMapper").isEqualTo(account));
//    }

}