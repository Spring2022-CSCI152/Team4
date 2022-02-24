package com.CSCI152.team4.server.Accounts.Classes;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "Business_Account")
public class BusinessAccount{


    public Integer getBusinessId() {
        return businessId;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    @Id
    private Integer businessId;

    private String businessName;

    @Embedded
    private ReportFormat reportFormat;

    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @NotEmpty(message = "Admin Accounts must not be empty!")
    @Size(min = 1)
    List<@NotEmpty  String> admins;

    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    List<String> employees;


    public BusinessAccount(){}

    public BusinessAccount(Integer id, String businessName) {
        this.businessId = id;
        this.admins = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public BusinessAccount(Integer id, String businessName, String admin) {
        this.businessId = id;
        this.businessName = businessName;
        if(admin != null) {
            this.admins = new ArrayList<String>();
            this.admins.add(admin);
        }
        this.employees = new ArrayList<String>();
    }

    public void addEmployee(String employeeId){
        if(!this.employees.contains(employeeId)){
            this.employees.add(employeeId);
        }
    }

    public void addAdmin(String adminId){
        if(!this.admins.contains(adminId)){
            this.admins.add(adminId);
        }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessAccount that = (BusinessAccount) o;
        return businessId.equals(that.businessId)
                && businessName.equals(that.businessName)
                && reportFormat.equals(that.reportFormat)
                && admins.equals(that.admins)
                && employees.equals(that.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId, businessName, reportFormat, admins, employees);
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}

