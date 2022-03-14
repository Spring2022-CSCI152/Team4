package com.CSCI152.team4.server.Accounts.Repos;


import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeAccountRepo extends CrudRepository<EmployeeAccount, String> {

    @Query("SELECT a FROM EmployeeAccountRepo a WHERE a.email = :email AND a.businessId = :businessId")
    Optional<EmployeeAccount> findByEmailAndBusinessId(@Param("email") String gardenId, @Param("businessId") Integer businessId);

}
