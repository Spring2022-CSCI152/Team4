package com.CSCI152.team4.server.Accounts.Endpoint;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("api/v1/session")
public class SessionController {

    private final SessionService service;

    @Autowired
    SessionController(SessionService service){
        this.service = service;
    }

    @PostMapping("api/v1/session_login")
    public ResponseEntity<WorkerAccount> userLogin(@RequestBody WorkerAccount accountInfo)
    {
        return new ResponseEntity<WorkerAccount>(service.userLogin(accountInfo), HttpStatus.OK);
    }

    @PostMapping("api/v1/session_logout")
    public ResponseEntity<WorkerAccount> userLogout(@RequestBody WorkerAccount accountInfo)
    {
        return new ResponseEntity<WorkerAccount>(service.userLogin(accountInfo), HttpStatus.OK);
    }
}
