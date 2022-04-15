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
        return sessionService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<Enum<HttpStatus>> logout(@RequestBody Request request){
        return sessionService.logout(request);
    }

    /**
     * Account Management Services
     * */
    @GetMapping("/my_account_info")
    public WorkerAccount getMyInfo(@RequestBody Request request){
        return managementService.getAccountInfo(request);
    }

    @GetMapping("/get_admin_account_info")
    public AdminAccount getAdminAccountInfo(@RequestBody TargetAccountRequest request){
        return managementService.getOtherAdminAccountInfo(request);
    }

    @GetMapping("/get_employee_account_info")
    public EmployeeAccount getEmployeeAccountInfo(@RequestBody TargetAccountRequest request){
        return managementService.getOtherEmployeeAccountInfo(request);
    }

    @PostMapping("/update_admin_account_info")
    public AdminAccount updateAdminAccountInfo(@RequestBody AdminAccount request){
        return managementService.updateAdminAccount(request);
    }

    @PostMapping("/update_employee_account_info")
    public EmployeeAccount updateEmployeeAccountInfo(@RequestBody EmployeeAccount request){
        return managementService.updateEmployeeAccount(request);
    }

    @PostMapping("/update_account_info_from_admin")
    public WorkerAccount updateEmployeeFromAdmin(@RequestBody UpdateFromAdminRequest request){
        return managementService.updateOtherFromAdmin(request);
    }

    @PostMapping("/update_permissions")
    public WorkerAccount updateEmployeePermissions(@RequestBody PermissionUpdateRequest request){
        return managementService.updateEmployeePermissions(request);
    }

    @GetMapping("/get_accounts")
    public List<WorkerAccount> getAccounts(@RequestBody Request request){
        return managementService.getAccounts(request);
    }

    /*
    * TODO: PROMOTE AND DEMOTE
    *  */

    @PostMapping("/promote")
    public WorkerAccount promote(@RequestBody TargetAccountRequest request){
        return managementService.promote(request);
    }

    @PostMapping("/demote")
    public WorkerAccount demote(@RequestBody TargetAccountRequest request){
        return managementService.demote(request);
    }
}
