package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.BusinessAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountRetriever;
import com.CSCI152.team4.server.Accounts.Requests.TargetAccountRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.Column;
import java.util.List;

@Component
public class AccountRetriever implements IAccountRetriever {

    private AccountsRepoInterface repos;

    @Autowired
    public AccountRetriever(AccountsRepoManager repos) {
        this.repos = repos;
    }

    @Override
    public WorkerAccount getAccountInfo(Request request) {
        if(repos.businessExists(request.getBusinessId())){
            WorkerAccount returnable
                    = repos.getAccountByEmailAndBusinessId(request.getAccountEmail(),
                    request.getBusinessId());
            /*This does not require an Admin, therefore do not call getReturnableOnAdminExists*/
            /*Do not return password!*/
            returnable.setPassword(null);

            return returnable;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Account Not Found!");
    }

    @Override
    public WorkerAccount getOtherAccountInfo(TargetAccountRequest request){
        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        String accountType = businessAccount.getAccountType(request.getAccountId().getAccountIdString());

        switch(accountType){
            case(BusinessAccount.adminAccountType):
                return getOtherAdminAccountInfo(request);
            case(BusinessAccount.employeeAccountType):
                return getOtherEmployeeAccountInfo(request);
            default:
                break;
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
    }

    @Override
    public List<WorkerAccount> getAccounts(Request request) {
        return repos.getAccountsByBusinessId(request.getBusinessId());
    }

    private AdminAccount getOtherAdminAccountInfo(TargetAccountRequest request) {
        AdminAccount ret = (AdminAccount) repos.getAccountByEmailAndBusinessId(
                        request.getTargetId().getEmail(), request.getTargetId().getBusinessId());
        ret.setPassword(null);

        return ret;
    }

    private EmployeeAccount getOtherEmployeeAccountInfo(TargetAccountRequest request) {
        EmployeeAccount ret = (EmployeeAccount) repos.getAccountByEmailAndBusinessId(
                request.getTargetId().getEmail(), request.getTargetId().getBusinessId());

        ret.setPassword(null);

        return ret;
    }

}
