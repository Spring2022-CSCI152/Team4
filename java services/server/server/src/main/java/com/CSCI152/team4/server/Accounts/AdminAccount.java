package com.CSCI152.team4.server.Accounts;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AdminAccount extends WorkerAccount{

    public AdminAccount(String email, String password,
                        String firstName, String lastName, String jobTitle) {
        super(email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
    }


}
