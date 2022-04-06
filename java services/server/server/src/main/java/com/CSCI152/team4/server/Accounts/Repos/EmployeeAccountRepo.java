package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeAccountRepo extends CrudRepository<EmployeeAccount, AccountId> {

    Optional<EmployeeAccount> findTopByEmailAndBusinessId(String email, Integer businessId);

    Boolean existsByEmailAndBusinessId(String email, Integer businessId);
}
