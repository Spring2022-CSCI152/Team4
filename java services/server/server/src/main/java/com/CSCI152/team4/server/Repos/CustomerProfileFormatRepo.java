package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileFormatRepo extends CrudRepository<CustomerProfileFormat, Integer> {
}
