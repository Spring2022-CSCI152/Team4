package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.BusinessRegistrationRequest;
import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountsController {

    private final AccountsService service;

    @Autowired
    public AccountsController(AccountsService service){
        this.service = service;
    }
    @PostMapping("api/v1/accounts/business_registration")
    public ResponseEntity<WorkerAccount> registerBusiness(@RequestBody BusinessRegistrationRequest request){
        return new ResponseEntity<WorkerAccount>(service.registerBusinessAccount(request), HttpStatus.OK);
    }
}
