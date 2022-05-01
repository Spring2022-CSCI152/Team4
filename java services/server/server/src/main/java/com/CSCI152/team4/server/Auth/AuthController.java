package com.CSCI152.team4.server.Auth;


import com.CSCI152.team4.server.Auth.Services.AuthenticationService;
import com.CSCI152.team4.server.Util.InstanceClasses.RequestDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthenticationService service;

    @Autowired
    public AuthController(AuthenticationService service) {
        this.service = service;
    }
    @PostMapping("/validate")
    public ResponseEntity<Enum<HttpStatus>> validate(@RequestBody RequestDAO requestDAO){
        return service.validateToken(requestDAO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Enum<HttpStatus>> refreshToken(@RequestBody RequestDAO requestDAO){
        return service.refreshToken(requestDAO);
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Enum<HttpStatus>> invalidate(@RequestBody RequestDAO requestDAO){
        return service.invalidateToken(requestDAO);
    }
}
