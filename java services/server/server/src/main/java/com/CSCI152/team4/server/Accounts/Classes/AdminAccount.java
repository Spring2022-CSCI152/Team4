package com.CSCI152.team4.server.Accounts.Classes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name="Admin_Account")
public class AdminAccount extends WorkerAccount {

    @Id
    private String accountId;

    public AdminAccount() {
    }

    public AdminAccount(String email, String password,
                        String firstName, String lastName, String jobTitle, int businessId) {
        super(businessId, email, password, firstName, lastName, Timestamp.valueOf(LocalDateTime.now()), jobTitle);
    }


}
