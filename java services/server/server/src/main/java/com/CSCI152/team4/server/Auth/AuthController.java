package com.CSCI152.team4.server.Auth;


import com.CSCI152.team4.server.Auth.Requests.AuthRequest;
import com.CSCI152.team4.server.Auth.Services.AuthenticationService;
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
    public ResponseEntity<Enum<HttpStatus>> validate(@RequestBody AuthRequest request){
        return service.validateToken(request.getToken(), request.getAccountId());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Enum<HttpStatus>> refreshToken(@RequestBody AuthRequest request){
        return service.refreshToken(request.getToken(), request.getAccountId());
    }

    @PostMapping("/invalidate")
    public ResponseEntity<Enum<HttpStatus>> invalidate(@RequestBody AuthRequest request){
        return service.invalidateToken(request.getToken(), request.getAccountId());
    }


}
