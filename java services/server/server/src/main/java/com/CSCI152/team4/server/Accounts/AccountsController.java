package com.CSCI152.team4.server.Accounts;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Classes.AdminAccount;
import com.CSCI152.team4.server.Accounts.Classes.EmployeeAccount;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import com.CSCI152.team4.server.Accounts.Requests.*;
import com.CSCI152.team4.server.Accounts.Services.AccountManagementService;
import com.CSCI152.team4.server.Accounts.Services.RegistrationService;
import com.CSCI152.team4.server.Accounts.Services.SessionService;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public WorkerAccount getMyInfo(@RequestBody Request request){

        return managementService.getAccountInfo(request);
    }

    @GetMapping("/get_admin_account_info")
    public AdminAccount getAdminAccountInfo(@RequestBody ObjectNode request){

        AccountId requestingAccount = getAccountIdFor("requestingAccount", request);
        AccountId targetAccount = getAccountIdFor("targetAccount", request);

        return managementService
                .getAdminAccountFromAdmin(request
                    .get("token").asText(), requestingAccount, targetAccount);
    }

    @GetMapping("/get_employee_account_info")
    public EmployeeAccount getEmployeeAccountInfo(@RequestBody ObjectNode request){

        AccountId requestingAccount = getAccountIdFor("requestingAccount", request);
        AccountId targetAccount = getAccountIdFor("targetAccount", request);

        return managementService
                .getEmployeeAccountFromAdmin(request
                        .get("token").asText(),requestingAccount, targetAccount);
    }

    private AccountId getAccountIdFor(String target, ObjectNode request){
        return new AccountId(request.get(target).get("accountIdString").asText(),
                request.get(target).get("email").asText(),
                request.get(target).get("businessId").asInt());
    }

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

    @PostMapping("/update_admin_account_info")
    public AdminAccount updateAdminAccountInfo(@RequestBody AdminAccount request){
        return managementService.updateAdminAccount(request);
    }

    @PostMapping("/update_employee_account_info")
    public EmployeeAccount updateEmployeeAccountInfo(@RequestBody EmployeeAccount request){
        return managementService.updateEmployeeAccount(request);
    }

    @PostMapping("/update_employee_account_from_admin")
    public ResponseEntity<Enum<HttpStatus>> updateEmployeeFromAdmin(@RequestBody ObjectNode request){

        AccountId adminAccountId = getAccountIdFor("adminAccount", request);

        return managementService.updateEmployeeAccountFromAdmin(request.get("token").asText(),
                adminAccountId, getEmployeeAccount(request.get("employee")));
    }

    private EmployeeAccount getEmployeeAccount(JsonNode request){
        String email, firstName, lastName, jobTitle;
        Integer businessId;

        email = request.get("email").asText();
        firstName = request.get("firstName").asText();
        lastName = request.get("lastName").asText();
        jobTitle = request.get("jobTitle").asText();
        businessId = request.get("businessId").asInt();

        return new EmployeeAccount(businessId,email, "", firstName, lastName, jobTitle);
    }

    @PostMapping("/update_permissions")
    public ResponseEntity<Enum<HttpStatus>> updateEmployeePermissions(@RequestBody PermissionUpdateRequest request){
        return managementService.updateEmployeePermissions(request);
    }

    @PostMapping("/login")
    public WorkerAccount login(@RequestBody LoginRequest request){
        return sessionService.login(request);
    }

    @PostMapping
    public ResponseEntity<Enum<HttpStatus>> logout(@RequestBody ObjectNode request){
        AccountId account = getAccountIdFor("account", request);
        return sessionService.logout(request.get("token").asText(), account);
    }
}
