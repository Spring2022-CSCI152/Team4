package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Requests.ProfileFormatUpdateRequest;
import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequest;
import com.CSCI152.team4.server.Util.InstanceClasses.*;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.Authenticator;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;

@Service
public class SettingsService {

    private final SecurityManager authenticator;
    private final SettingsRepoInterface repoManager;
    private final AccountsRepoInterface accounts;

    @Autowired
    public SettingsService(SecurityUtil authenticator, SettingsRepoManager repoManager, AccountsRepoManager accounts) {
        this.authenticator = authenticator;
        this.repoManager = repoManager;
        this.accounts = accounts;
    }

    public void setReportFormat(ReportFormatUpdateRequest request){
        authenticator.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.REPORT_FORMAT);
        validateBusinessId(request);
        validateAndSaveReportFormat(request.getBusinessId(), request.getReportFormat());

    }

    public ReportFormat getReportFormat(Request request){
        authenticator.validateToken(request.getAccountId(), request.getToken());
        validateBusinessId(request);
        return repoManager.getReportFormatIfExists(request.getBusinessId());
    }

    public void setProfileFormat(ProfileFormatUpdateRequest request){
        authenticator.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.PROFILES_FORMAT);
        validateBusinessId(request);
        validateAndSaveProfileFormat(request.getBusinessId(), request.getProfileFormat());

    }

    public CustomerProfileFormat getProfileFormat(Request request){
        authenticator.validateToken(request.getAccountId(), request.getToken());
        validateBusinessId(request);
        return repoManager.getCustomerProfileFormatIfExists(request.getBusinessId());
    }

    private boolean isAdminForBusiness(Integer businessId, String accountIdString){
        return (accounts.getBusinessIfExists(businessId)
                .getAdmins()
                .contains(accountIdString));
    }

    //Checks the stored business accounts' admins and employee lists
    //returns true if id is found
    private boolean isValidBusinessIdAndAccount(Integer businessId, String accountId){
        return (accounts.getBusinessIfExists(businessId).getAdmins().contains(accountId)
        || accounts.getBusinessIfExists(businessId).getEmployees().contains(accountId));
    }

    private void validateAndSaveReportFormat(Integer businessId, ReportFormat reportFormat){
        validateReportFormat(reportFormat);
        repoManager.saveReportFormat(reportFormat);
    }

    private void validateReportFormat(ReportFormat reportFormat){
        if(reportFormat.getBusinessId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Business Id!");
        }
    }

    private void validateAndSaveProfileFormat(Integer businessId, CustomerProfileFormat profileFormat){
        validateProfileFormat(profileFormat);
        repoManager.saveCustomerProfileFormat(profileFormat);
    }

    private void validateProfileFormat(CustomerProfileFormat profileFormat){
        if(profileFormat.getBusinessId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No Business Id!");
        }
    }

    private void validateBusinessId(Request request){
        if(isValidBusinessIdAndAccount(request.getBusinessId(), request.getAccountIdString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Business ID");
        }
    }


}
