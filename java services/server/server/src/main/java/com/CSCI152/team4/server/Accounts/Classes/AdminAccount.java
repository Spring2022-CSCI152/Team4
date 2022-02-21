package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Classes.WorkerAccount;

import javax.persistence.Entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity(name="Admin_Account")
public class AdminAccount extends WorkerAccount {

    public AdminAccount(String email, String password,
                        String firstName, String lastName, String jobTitle) {
        super(email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
    }


}
