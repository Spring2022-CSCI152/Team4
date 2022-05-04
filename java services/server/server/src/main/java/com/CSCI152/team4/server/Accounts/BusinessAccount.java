package com.CSCI152.team4.server.Accounts;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class BusinessAccount extends Account{


    private String businessName;
    private ReportFormat reportFormat;

    @NotEmpty(message = "Admin Accounts must not be empty!")
    List<String> admins;

    List<String> employees;


}
