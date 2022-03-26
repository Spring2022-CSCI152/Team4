package com.CSCI152.team4.server.AccountsReformat.Repos;

import com.CSCI152.team4.server.AccountsReformat.AccountClasses.BusinessAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessAccountRepo extends CrudRepository<BusinessAccount, Integer> {

}
