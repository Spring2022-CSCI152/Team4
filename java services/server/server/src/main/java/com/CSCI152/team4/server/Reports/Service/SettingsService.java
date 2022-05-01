package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Requests.ProfileFormatUpdateRequestDAO;
import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequestDAO;
import com.CSCI152.team4.server.Util.InstanceClasses.*;
import com.CSCI152.team4.server.Util.Interfaces.AccountsRepoInterface;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SettingsService {

    private final SecurityManager authenticator;
    private final SettingsRepoInterface repoManager;
    private final AccountsRepoInterface accounts;

    @Autowired
    public SettingsService(SecurityManager authenticator, SettingsRepoInterface repoManager, AccountsRepoInterface accounts) {
        this.authenticator = authenticator;
        this.repoManager = repoManager;
        this.accounts = accounts;
    }

    public void setReportFormat(ReportFormatUpdateRequestDAO request){
        authenticator.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.REPORT_FORMAT);
        validateBusinessId(request);
        validateAndSaveReportFormat(request.getBusinessId(), request.getReportFormat());

    }

    public ReportFormat getReportFormat(RequestDAO requestDAO){
        authenticator.validateToken(requestDAO.getAccountId(), requestDAO.getToken());
        validateBusinessId(requestDAO);
        return repoManager.getReportFormatIfExists(requestDAO.getBusinessId());
    }

    public void setProfileFormat(ProfileFormatUpdateRequestDAO request){
        authenticator.validateTokenAndPermission(request.getAccountId(), request.getToken(), Permissions.PROFILES_FORMAT);
        validateBusinessId(request);
        validateAndSaveProfileFormat(request.getBusinessId(), request.getProfileFormat());

    }

    public CustomerProfileFormat getProfileFormat(RequestDAO requestDAO){
        authenticator.validateToken(requestDAO.getAccountId(), requestDAO.getToken());
        validateBusinessId(requestDAO);
        return repoManager.getCustomerProfileFormatIfExists(requestDAO.getBusinessId());
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
        if(reportFormat == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Request, Report Format is null");
        }
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

    private void validateBusinessId(RequestDAO requestDAO){
        if(!isValidBusinessIdAndAccount(requestDAO.getBusinessId(), requestDAO.getAccountIdString())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Business ID");
        }
    }


}
