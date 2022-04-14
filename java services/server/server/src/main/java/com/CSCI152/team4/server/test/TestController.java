package com.CSCI152.team4.server.test;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {


    @PostMapping("/embedded_accounts")
    public void test(@RequestBody EmbeddedAccountsRequest request){
        System.out.println(request.toString());
    }
}
