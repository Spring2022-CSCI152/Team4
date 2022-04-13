package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequest;
import com.CSCI152.team4.server.Repos.ReportFormatRepo;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.SettingsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SettingsService {

    private final TokenAuthenticator authenticator;
    private final SettingsRepoManager repoManager;
    private final AccountsRepoManager accounts;
    @Autowired
    public SettingsService(TokenAuthenticator authenticator, SettingsRepoManager repoManager, AccountsRepoManager accounts) {
        this.authenticator = authenticator;
        this.repoManager = repoManager;
        this.accounts = accounts;
    }

    public void setReportFormat(ReportFormatUpdateRequest request){
        authenticator.validateToken(request.getToken(), request.getRequestingAccountId());
        //Ensure requesting account Id belongs to submitted
        //business Id
        if(isValidBusinessIdAndAccount(request.getBusinessId(), request.getRequestingAccountId())){
            validateAndSaveReportFormat(request.getBusinessId(), request.getReportFormat());
        }

    }

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

}
