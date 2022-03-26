package com.CSCI152.team4.server.AccountsReformat.Repos;

import com.CSCI152.team4.server.AccountsReformat.AccountClasses.AccountId;
import com.CSCI152.team4.server.AccountsReformat.AccountClasses.AdminAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAccountRepo extends CrudRepository<AdminAccount, AccountId> {

}
