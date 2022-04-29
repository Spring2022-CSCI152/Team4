package com.CSCI152.team4.server.Util.InstanceClasses;


import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Repos.EmployeeAccountRepo;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides an interface for interactions with
 * the Business, Admin, and Employee Repos. This
 * class is intended to lower coupling and
 * increase overall code cohesion.
 * */
@Service
public class AccountsRepoManager implements AccountsRepoInterface {

    private final BusinessAccountRepo business;
    private final EmployeeAccountRepo employees;
    private final AdminAccountRepo admins;


    @Autowired
    AccountsRepoManager(BusinessAccountRepo business, EmployeeAccountRepo employees, AdminAccountRepo admins){
        this.business = business;
        this.admins = admins;
        this.employees = employees;
    }

    public AdminAccount saveAdminAccount(AdminAccount account){
        return admins.save(account);
    }

    public EmployeeAccount saveEmployeeAccount(EmployeeAccount account){
        return employees.save(account);
    }

    public BusinessAccount saveBusinessAccount(BusinessAccount account){
        return business.save(account);
    }

    public boolean businessExists(Integer businessId){
        return business.existsById(businessId);
    }

    public boolean adminExists(AccountId accountId){
        return admins.existsById(accountId);
    }

    public boolean employeeExists(AccountId accountId){
        return employees.existsById(accountId);
    }


    public BusinessAccount getBusinessIfExists(Integer businessId){
        if(business.existsById(businessId)){
            return business.findById(businessId).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account Not Found!");
    }

    public AdminAccount getAdminIfExists(AccountId accountId){
        if(admins.existsById(accountId)){
            return admins.findById(accountId).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found!");
    }

    public EmployeeAccount getEmployeeIfExists(AccountId accountId){
        if(employees.existsById(accountId)){
            return employees.findById(accountId).get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found!");
    }

    public WorkerAccount getAccountByEmailAndBusinessId(String email, Integer businessId){

        Optional returnable = Optional.empty();
        if(employees.existsByEmailAndBusinessId(email, businessId)){
            returnable =
                     employees.findTopByEmailAndBusinessId(email, businessId);
        }
        else if(admins.existsByEmailAndBusinessId(email, businessId)){
            returnable =
                    admins.findTopByEmailAndBusinessId(email, businessId);
        }

        if(returnable != null && returnable.isPresent()){
            return (WorkerAccount) returnable.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account Not Found!");
    }

    public List<WorkerAccount> getAccountsByBusinessId(Integer businessId){
        List<AdminAccount> adminList = admins.findAllByBusinessId(businessId);
        List<EmployeeAccount> empList = employees.findAllByBusinessId(businessId);

        List<WorkerAccount> retList = new ArrayList<>();
        for(AdminAccount a : adminList){
            a.setPassword(null);
            retList.add(a);
        }
        for(EmployeeAccount e : empList){
            e.setPassword(null);
            retList.add(e);
        }
        return retList;
    }

    @Override
    public boolean removeEmployeeAccount(AccountId accountId) {
        if(employees.existsById(accountId)){
            employees.deleteById(accountId);
            return !employees.existsById(accountId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found!");
    }

    @Override
    public boolean removeAdminAccount(AccountId accountId) {
        if(admins.existsById(accountId)){
            admins.deleteById(accountId);
            return !admins.existsById(accountId);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account Not Found!");
    }
}
