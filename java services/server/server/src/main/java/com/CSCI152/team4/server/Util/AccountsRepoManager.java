package com.CSCI152.team4.server.Util;


import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Repos.AdminAccountRepo;
import com.CSCI152.team4.server.Repos.BusinessAccountRepo;
import com.CSCI152.team4.server.Repos.EmployeeAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Provides an interface for interactions with
 * the Business, Admin, and Employee Repos. This
 * class is intended to lower coupling and
 * increase overall code cohesion.
 * */
@Service
public class AccountsRepoManager implements AccountsRepoInterface{

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
        System.out.println(account.toString());
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
        if(businessExists(businessId)){
            Optional<BusinessAccount> optionalBusinessAccount
                    = business.findById(businessId);

            if(optionalBusinessAccount.isPresent()){
                return optionalBusinessAccount.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Something went wrong!");
            }
        }
        return null;
    }

    public AdminAccount getAdminIfExists(AccountId accountId){
        if(adminExists(accountId)){
            Optional<AdminAccount> optionalAdminAccount
                    = admins.findById(accountId);

            if(optionalAdminAccount.isPresent()){
                return optionalAdminAccount.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Something went wrong!");
            }
        }
        return null;
    }

    public EmployeeAccount getEmployeeIfExists(AccountId accountId){
        if(employeeExists(accountId)){
            Optional<EmployeeAccount> optionalEmployeeAccount
                    = employees.findById(accountId);

            if(optionalEmployeeAccount.isPresent()){
                return optionalEmployeeAccount.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Something went wrong!");
            }
        }
        return null;
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
        return null;
    }

}
