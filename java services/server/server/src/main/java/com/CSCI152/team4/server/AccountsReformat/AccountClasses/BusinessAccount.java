package com.CSCI152.team4.server.AccountsReformat.AccountClasses;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormatBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "Business_Account")
@SequenceGenerator(name = "business_id_seq", initialValue = 100, allocationSize = 899)
public class BusinessAccount {

    public static final String adminAccountType = "admin";
    public static final String employeeAccountType = "employee";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "business_id_seq")
    private Integer businessId;

    @NotBlank
    private String businessName;

    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @NotEmpty(message = "Admin Accounts must not be empty!")
    @Size(min = 1)
    private List<String> admins;

    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private List<String> employees;

    @Embedded
    private ReportFormat reportFormat;

    //@Embedded
    //private ProfileFormat profileFormat;

    @Transient
    private HashMap<String, String> accountMapper;

    public BusinessAccount() {
    }

    public BusinessAccount(String businessName, String adminId) {
        this.businessName = businessName;
        createEmployeeListsAndAddAdmin(adminId);
        mapAccounts();
        buildDefaultReportFormat();
        buildDefaultProfileFormat();
    }


    private void createEmployeeListsAndAddAdmin(String adminId){
        this.admins = new ArrayList<String>(Collections.singleton(adminId));
        this.employees = new ArrayList<>();
    }

    private void mapAccounts(){
        if (this.accountMapper == null) this.accountMapper = new HashMap<>();

        for(String id : this.admins){
            this.accountMapper.put(id, adminAccountType);
        }

        for(String id : this.employees){
            this.accountMapper.put(id, employeeAccountType);
        }
    }

    private void buildDefaultReportFormat(){
        this.reportFormat = new ReportFormatBuilder().build();
    }

    private void buildDefaultProfileFormat(){
        //this.profileFormat = new ProfileFormatBuilder().build();
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public List<String> getAdmins(){
        return admins;
    }

    public List<String> getEmployees(){
        return employees;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getAccountType(String accountId){
        return this.accountMapper.get(accountId);
    }

    public void addAdmin(String accountId){
        if(!this.admins.contains(accountId)){
            this.admins.add(accountId);
        }
        this.accountMapper.put(accountId, adminAccountType);
    }

    public void addEmployee(String accountId){
        if(!this.employees.contains(accountId)){
            this.admins.add(accountId);
        }
        this.accountMapper.put(accountId, employeeAccountType);
    }

    public void removeAdmin(String accountId){
        this.admins.remove(accountId);
        if(this.accountMapper.get(accountId).equals(adminAccountType)){
            this.accountMapper.remove(accountId);
        }
    }

    public void removeEmployee(String accountId){
        this.employees.remove(accountId);
        if(this.accountMapper.get(accountId).equals(employeeAccountType)){
            this.accountMapper.remove(accountId);
        }
    }

    public void promote(String accountId){
        if(this.accountMapper.get(accountId) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found!");
        }

        this.employees.remove(accountId);
        this.admins.add(accountId);
        this.accountMapper.put(accountId, adminAccountType);
    }

    public void demote(String accountId){
        if(this.accountMapper.get(accountId) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No account found!");
        }

        this.admins.remove(accountId);
        this.employees.add(accountId);
        this.accountMapper.put(accountId, employeeAccountType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusinessAccount)) return false;
        BusinessAccount that = (BusinessAccount) o;
        return getBusinessId().equals(that.getBusinessId())
                && getBusinessName().equals(that.getBusinessName())
                && getAdmins().equals(that.getAdmins())
                && Objects.equals(getEmployees(), that.getEmployees())
                && Objects.equals(getReportFormat(), that.getReportFormat());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessId(), getBusinessName(),
                getAdmins(), getEmployees(), getReportFormat());
    }
}
