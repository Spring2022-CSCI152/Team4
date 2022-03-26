package com.CSCI152.team4.server.AccountsReformat.Repos;

import com.CSCI152.team4.server.AccountsReformat.AccountClasses.AccountId;
import com.CSCI152.team4.server.AccountsReformat.AccountClasses.AdminAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminAccountRepo extends CrudRepository<AdminAccount, AccountId> {

    Optional<AdminAccount> findTopByEmailAndBusinessId(String email, Integer businessId);

    Boolean existsByEmailAndBusinessId(String email, Integer businessId);
}
