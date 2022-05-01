package com.CSCI152.team4.server.Accounts;

import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
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
    public AdminAccount registerBusinessAccount(@RequestBody BusinessRequest request){
        System.out.println("Registration Request Received");
        return registrationService.registerBusiness(request);
    }

    @PostMapping("/register_admin")
    public ResponseEntity<Enum<HttpStatus>> registerAdmin(@RequestBody AdminRequest request){
        return registrationService.registerAdmin(request);
    }

    @PostMapping("/register_employee")
    public ResponseEntity<Enum<HttpStatus>> registerEmployee(@RequestBody EmployeeRequest request){
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
    public ResponseEntity<Enum<HttpStatus>> logout(@RequestBody Request request){
        System.out.println("Logging out for: " + request.getAccountEmail());
        return sessionService.logout(request);
    }

    /**
     * Account Management Services
     * */
    @GetMapping("/my_account_info")
    public WorkerAccount getMyInfo(@RequestBody Request request){
        return managementService.getAccountInfo(request);
    }

    @GetMapping("/get_other_account_info")
    public WorkerAccount getAdminAccountInfo(@RequestBody TargetAccountRequest request){
        return managementService.getOtherAccountInfo(request);
    }

    @GetMapping("/get_accounts")
    public List<WorkerAccount> getAccounts(@RequestBody Request request){
        return managementService.getAccounts(request);
    }

    @PutMapping("/update_info")
    public WorkerAccount updateInfo(@RequestBody UpdateRequest request){
        return managementService.updateInfo(request);
    }

    @PutMapping("/update_other_info")
    public WorkerAccount updateOtherInfo(@RequestBody UpdateOtherRequest request){
        return managementService.updateOther(request);
    }


    @PutMapping("/update_permissions")
    public WorkerAccount updateEmployeePermissions(@RequestBody PermissionUpdateRequest request){
        return managementService.updateEmployeePermissions(request);
    }

    @PutMapping("/promote")
    public WorkerAccount promote(@RequestBody TargetAccountRequest request){
        return managementService.promote(request);
    }

    @PutMapping("/demote")
    public WorkerAccount demote(@RequestBody TargetAccountRequest request){
        return managementService.demote(request);
    }
}
