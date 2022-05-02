package com.CSCI152.team4.server.Accounts;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.*;
import com.CSCI152.team4.server.Accounts.Services.AccountManagementService;
import com.CSCI152.team4.server.Accounts.Services.RegistrationService;
import com.CSCI152.team4.server.Accounts.Services.SessionService;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Registration Services
     * */
    @PostMapping("/register_business")
    public AdminAccount registerBusinessAccount(@RequestBody BusinessRequestDAO request){
        System.out.println("Registration Request Received");
        return registrationService.registerBusiness(request);
    }

    @PostMapping("/register_admin")
    public ResponseEntity<Enum<HttpStatus>> registerAdmin(@RequestBody AdminRequestDAO request){
        System.out.println("Registering Admin: " + request.getAccountEmail());
        return registrationService.registerAdmin(request);
    }

    @PostMapping("/register_employee")
    public ResponseEntity<Enum<HttpStatus>> registerEmployee(@RequestBody EmployeeRequestDAO request){
        System.out.println("Registering Employee: " + request.getAccountEmail());
        return registrationService.registerEmployee(request);
    }

    /**
     * Session Services
     * */
    @PostMapping("/login")
    public WorkerAccount login(@RequestBody LoginRequest request){
        System.out.println("Logging in for: " + request.getEmail());
        return sessionService.login(request);
    }

    @PutMapping("/logout")
    public ResponseEntity logoutPut(@RequestBody Request request){
        System.out.println("Logging Out: " + request.getAccountEmail());
        return sessionService.logout(request);
    }

    @PostMapping("/logout")
    public ResponseEntity logoutPost(@RequestBody Request request){
        System.out.println("Logging Out: " + request.getAccountEmail());
        return sessionService.logout(request);
    }


    /**
     * Account Management Services
     * */
    @PostMapping("/my_account_info")
    public WorkerAccount getMyInfo(@RequestBody Request request){
        System.out.println("Getting Acct Info: " + request.getAccountEmail());
        return managementService.getAccountInfo(request);
    }

    @PostMapping("/get_other_account_info")
    public WorkerAccount getOtherAccountInfo(@RequestBody TargetAccountRequestDAO request){
        System.out.println("Getting Other Acct Info: " + request.getAccountEmail());
        return managementService.getOtherAccountInfo(request);
    }

    @PostMapping("/get_accounts")
    public List<WorkerAccount> getAccounts(@RequestBody Request request){
        System.out.println("Getting All Other Acct Info: " + request.getAccountEmail());
        return managementService.getAccounts(request);
    }

    @PutMapping("/update_info")
    public WorkerAccount updateInfo(@RequestBody UpdateRequestDAO request){
        System.out.println("Updating Acct Info: " + request.getAccountEmail());
        return managementService.updateInfo(request);
    }

    @PutMapping("/update_other_info")
    public WorkerAccount updateOtherInfo(@RequestBody UpdateOtherRequestDAO request){
        System.out.println("Updating Other Acct Info: " + request.getAccountEmail());
        return managementService.updateOther(request);
    }


    @PutMapping("/update_permissions")
    public WorkerAccount updateEmployeePermissions(@RequestBody PermissionUpdateRequestDAO request){
        System.out.println("Updating Permissions: " + request.getAccountEmail());
        return managementService.updateEmployeePermissions(request);
    }

    @PutMapping("/promote")
    public WorkerAccount promote(@RequestBody TargetAccountRequestDAO request){
        System.out.println("Promoting Acct: " + request.getAccountEmail());
        return managementService.promote(request);
    }

    @PutMapping("/demote")
    public WorkerAccount demote(@RequestBody TargetAccountRequestDAO request){
        System.out.println("Demoting Acct: " + request.getAccountEmail());
        return managementService.demote(request);
    }
}
