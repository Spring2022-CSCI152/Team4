package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class BusinessAccount extends Account {


    private String businessName;
    private ReportFormat reportFormat;

    @Column(nullable = false)
    @NotEmpty(message = "Admin Accounts must not be empty!")
    List<String> admins;

    List<String> employees;

    public BusinessAccount(){
        super();
    }

    public BusinessAccount(int id, String businessName) {
        super(id);
        this.admins = new ArrayList<String>();
        this.employees = new ArrayList<String>();
    }

    public BusinessAccount(int id, String businessName, ReportFormat reportFormat, String admin) {
        super(id);
        this.businessName = businessName;
        this.reportFormat = reportFormat;
        this.admins = new ArrayList<String>();
        this.admins.add(admin);
    }

    public void addEmployee(String employeeId){

    }

    public void addAdmin(String adminId){

    }

    public void removeEmployee(String employeeId){

    }

    public void removeAdmin(String adminId){

    }


    public void setReportFormat(ReportFormat reportFormat){
        this.reportFormat = reportFormat;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public List<String> getEmployees() {
        return employees;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}

