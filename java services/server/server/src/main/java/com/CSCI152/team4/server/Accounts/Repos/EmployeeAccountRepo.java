package com.CSCI152.team4.server.Accounts.Repos;


import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeAccountRepo extends CrudRepository<EmployeeAccount, String> {


    Optional<EmployeeAccount> findFirstByEmailAndBusinessId(@Param("email") String email, @Param("businessId") Integer businessId);

}
