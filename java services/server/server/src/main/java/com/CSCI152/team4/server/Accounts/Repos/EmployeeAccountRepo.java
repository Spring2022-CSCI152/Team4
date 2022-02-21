package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeAccountRepo extends CrudRepository<EmployeeAccount, String> {
}
