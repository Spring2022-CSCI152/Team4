package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Classes.*;

import java.util.List;

public interface AccountsRepoInterface {

    AdminAccount saveAdminAccount(AdminAccount account);

    EmployeeAccount saveEmployeeAccount(EmployeeAccount account);

    BusinessAccount saveBusinessAccount(BusinessAccount account);

    boolean businessExists(Integer businessId);

    boolean adminExists(AccountId accountId);

    boolean employeeExists(AccountId accountId);

    BusinessAccount getBusinessIfExists(Integer businessId);

    AdminAccount getAdminIfExists(AccountId accountId);

    EmployeeAccount getEmployeeIfExists(AccountId accountId);

    WorkerAccount getAccountByEmailAndBusinessId(String email, Integer businessId);

    List<WorkerAccount> getAccountsByBusinessId(Integer businessId);
}
