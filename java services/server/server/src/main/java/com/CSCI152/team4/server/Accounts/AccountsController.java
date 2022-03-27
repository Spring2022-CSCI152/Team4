package com.CSCI152.team4.server.Accounts;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Services.AccountManagementService;
import com.CSCI152.team4.server.Accounts.Services.RegistrationService;
import com.CSCI152.team4.server.Accounts.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountsController {

    /**
     * This controller will map to 3 services all related to accounts.
     *
     * Due to the large number of activities the 'Accounts' endpoint is
     * responsible for, each activity is grouped with related activities.
     * Each service will be responsible for only one of these groups. They are listed
     * below
     *
     * Session:
     *      logIn
     *      logOut
     *
     * Registration
     *      signUp
     *      accountRegistration
     *
     * Account Management
     *      getAccountInfo
     *      updateAccountInfo
     *      getPermissions
     *      setPermissions
     * */

    private final AccountManagementService managementService;
    private final RegistrationService registrationService;
    private final SessionService sessionService;

    @Autowired
    public AccountsController(AccountManagementService managementService,
                              RegistrationService registrationService,
                              SessionService sessionService) {
        this.managementService = managementService;
        this.registrationService = registrationService;
        this.sessionService = sessionService;
    }

    @GetMapping("/my_account_info")
    public WorkerAccount getMyInfo(@RequestBody WorkerAccount account){

        return managementService.getAccountInfo(account);
    }

    @GetMapping("/get_admin_account_info")
    public AdminAccount getAdminAccountInfo(@RequestBody String token,
                                            @RequestBody AccountId requestingAccount,
                                            @RequestBody AccountId targetAccount){
        return managementService.getAdminAccountFromAdmin(token, requestingAccount, targetAccount);
    }

    @GetMapping("/get_employee_account_info")
    public EmployeeAccount getEmployeeAccountInfo(@RequestBody String token,
                                               @RequestBody AccountId requestingAccount,
                                               @RequestBody AccountId targetAccount){
        return managementService.getEmployeeAccountFromAdmin(token, requestingAccount, targetAccount);
    }
}
