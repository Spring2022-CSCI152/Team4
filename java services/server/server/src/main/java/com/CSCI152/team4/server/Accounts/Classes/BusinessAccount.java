package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Business_Account")
public class BusinessAccount extends Account {


    @Id
    private int businessId;
    private String businessName;

    @Embedded
    private ReportFormat reportFormat;

    @Column(nullable = false)
    @ElementCollection
    @NotEmpty(message = "Admin Accounts must not be empty!")
    List<String> admins;
    @ElementCollection
    List<String> employees;



    public BusinessAccount(){}

    public BusinessAccount(int id, String businessName) {
        super(id);
        this.admins = new ArrayList<String>();
        this.employees = new ArrayList<String>();
    }

    public BusinessAccount(int id, String businessName, String admin) {
        super(id);
        this.businessName = businessName;
        this.admins = new ArrayList<String>();
        this.admins.add(admin);
        this.employees = new ArrayList<String>();
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

