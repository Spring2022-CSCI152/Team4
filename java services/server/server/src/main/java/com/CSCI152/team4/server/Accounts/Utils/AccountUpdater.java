package com.CSCI152.team4.server.Accounts.Utils;

import com.CSCI152.team4.server.Accounts.Classes.*;
import com.CSCI152.team4.server.Accounts.Interfaces.IAccountUpdater;
import com.CSCI152.team4.server.Accounts.Requests.UpdateOtherRequestDAO;
import com.CSCI152.team4.server.Accounts.Requests.UpdateRequestDAO;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AccountUpdater implements IAccountUpdater {

    private final AccountsRepoInterface repos;

    @Autowired
    public AccountUpdater(AccountsRepoInterface repos) {
        this.repos = repos;
    }

    @Override
    public WorkerAccount updateOther(UpdateOtherRequestDAO request) {
        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        String accountType = businessAccount.getAccountType(request.getTargetId().getAccountIdString());

        return updateAndSave(request.getTargetId(), accountType, request);
    }

    @Override
    public WorkerAccount updateSelf(UpdateRequestDAO request) {
        BusinessAccount businessAccount = repos.getBusinessIfExists(request.getBusinessId());

        String accountType = businessAccount.getAccountType(request.getAccountId().getAccountIdString());

        return updateAndSave(request.getAccountId(), accountType, request);
    }


    private WorkerAccount updateAndSave(AccountId accountToUpdate, String accountType, UpdateRequestDAO request){
        /*BECAUSE OF DIFFERENT REPOS, IMPLEMENTATION WAS STUCK TO THIS METHOD, IT VIOLATES OPEN/CLOSE PRINCIPAL*/

        if(accountType.equals(BusinessAccount.adminAccountType)){
            return updateAndSaveAdminAccount(accountToUpdate, request);
        }
        else if(accountType.equals(BusinessAccount.employeeAccountType)){
            return updateAndSaveEmployeeAccount(accountToUpdate, request);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Account Type!");
    }

    private AdminAccount updateAndSaveAdminAccount(AccountId accountIdToUpdate, UpdateRequestDAO request){

        AdminAccount accountToSave = repos.getAdminIfExists(accountIdToUpdate);

        updateMutableAccountFields(request, accountToSave);

        AdminAccount returnable = repos.saveAdminAccount(accountToSave);
        /*Do not return password!*/
        returnable.setPassword(null);

        return  returnable;
    }

    private EmployeeAccount updateAndSaveEmployeeAccount(AccountId accountIdToUpdate, UpdateRequestDAO request){
        EmployeeAccount accountToSave = repos.getEmployeeIfExists(accountIdToUpdate);

        updateMutableAccountFields(request, accountToSave);

        EmployeeAccount returnable = repos.saveEmployeeAccount(accountToSave);
        /*Do not return password!*/
        returnable.setPassword(null);

        return returnable;
    }

    private void updateMutableAccountFields(UpdateRequestDAO newInfo, WorkerAccount originalInfo){

        String fNameToUpdate = newInfo.getFirstName();
        String lNameToUpdate = newInfo.getLastName();
        String jobTitleToUpdate = newInfo.getJobTitle();
        String newPassword = newInfo.getPassword();

        if(fNameToUpdate != null && !fNameToUpdate.isBlank()){
            originalInfo.setFirstName(fNameToUpdate);
        }

        if(lNameToUpdate != null && !lNameToUpdate.isBlank()){
            originalInfo.setLastName(lNameToUpdate);
        }

        if(jobTitleToUpdate != null && !jobTitleToUpdate.isBlank()){
            originalInfo.setJobTitle(jobTitleToUpdate);
        }

        if(newPassword != null && !newPassword.isBlank()){
            String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            originalInfo.setPassword(encryptedPassword);
        }
    }
}
