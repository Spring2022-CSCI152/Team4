package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import org.springframework.data.repository.CrudRepository;

public interface BusinessAccountRepo extends CrudRepository<BusinessAccount, Integer> {
}
