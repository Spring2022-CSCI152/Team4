package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountTransposer;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountStatusChanger;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountStatusChanger implements IAccountStatusChanger {

    private final AccountsRepoInterface accounts;
    private final IAccountTransposer transposer;

    @Autowired
    public AccountStatusChanger(AccountsRepoInterface accounts, IAccountTransposer transposer) {
        this.accounts = accounts;
        this.transposer = transposer;
    }
    @Override
    public WorkerAccount promote(TargetAccountRequest request) {
        BusinessAccount businessAccount = accounts.getBusinessIfExists(request.getBusinessId());
        if(businessAccount == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account not found!");
        }
        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
                .equals(BusinessAccount.employeeAccountType)){

            AdminAccount ret = promoteToAdmin(businessAccount, request.getTargetId());
            ret.setPassword(null);
            return ret;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target is not Employee Account");
    }
    @Override
    public WorkerAccount demote(TargetAccountRequest request) {
        BusinessAccount businessAccount = accounts.getBusinessIfExists(request.getBusinessId());
        if(businessAccount == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account not found!");
        }
        if (businessAccount.getAccountType(request.getTargetId().getAccountIdString())
                .equals(BusinessAccount.adminAccountType)){

            EmployeeAccount ret = demoteToEmployee(businessAccount, request.getTargetId());
            ret.setPassword(null);
            return ret;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target is not Admin Account");
    }

    private AdminAccount promoteToAdmin(BusinessAccount businessAccount, AccountId accountToPromote){
        businessAccount.promote(accountToPromote.getAccountIdString());
        accounts.saveBusinessAccount(businessAccount);
        try{
//            /* Transpose EmployeeAccount to AdminAccount*/
            AdminAccount newAdmin = (AdminAccount) transposer.transposeTo(AdminAccount.class, accounts.getEmployeeIfExists(accountToPromote));
//            /*Save to AdminTable*/
            accounts.saveAdminAccount(newAdmin);
//            /*Remove from EmployeeTable*/
            accounts.removeEmployeeAccount(accountToPromote);
//            /* Return database successfully swapped entries*/
            if(accounts.adminExists(accountToPromote) && !accounts.employeeExists(accountToPromote)){
                return accounts.getAdminIfExists(accountToPromote);
            }
        }catch(Exception e){}
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during promotion!");
    }

    private EmployeeAccount demoteToEmployee(BusinessAccount businessAccount, AccountId accountToDemote){
        businessAccount.demote(accountToDemote.getAccountIdString());
        accounts.saveBusinessAccount(businessAccount);
        try{
//            /*Transpose AdminAccount to EmployeeAccount*/
            EmployeeAccount newEmployee = (EmployeeAccount) transposer.transposeTo(EmployeeAccount.class, accounts.getAdminIfExists(accountToDemote));
//            /*Save to Employee Table*/
            accounts.saveEmployeeAccount(newEmployee);
//            /*Delete From Admin Table*/
            accounts.removeAdminAccount(accountToDemote);
//            /* Return database successfully swapped entries*/
            if(!accounts.adminExists(accountToDemote) && accounts.employeeExists(accountToDemote)){
                return accounts.getEmployeeIfExists(accountToDemote);
            }
        }catch(Exception e){}
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something went wrong during demotion!");
    }
}