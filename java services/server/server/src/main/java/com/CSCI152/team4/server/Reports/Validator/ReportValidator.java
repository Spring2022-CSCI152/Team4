package com.CSCI152.team4.server.Reports.Validator;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Util.InstanceClasses.SettingsRepoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/*This class serves as a utility to ensure only the desired input is
* accepted and saved*/
@Component
public class ReportValidator {

    private final SettingsRepoManager settingsRepoManager;

    @Autowired
    public ReportValidator(SettingsRepoManager settingsRepoManager) {
        this.settingsRepoManager = settingsRepoManager;
    }

    public void validateReport(Report report){
        if(!settingsRepoManager.reportFormatExistsById(report.getBusinessId())){
            throw new ResponseStatusException(HttpStatus.OK, "Business Not Found!");
        }
        ReportFormat reportFormat =
                settingsRepoManager.getReportFormatIfExists(report.getBusinessId());
    }

    public void validateProfile(Profile profile){
        if(!settingsRepoManager.reportFormatExistsById(profile.getBusinessId())){
            throw new ResponseStatusException(HttpStatus.OK, "Business Not Found!");
        }
        CustomerProfileFormat profileFormat =
                settingsRepoManager.getCustomerProfileIfExists(profile.getBusinessId());
    }

    public void validateProfiles(List<Profile> profileList){

        for(Profile profile : profileList){
            validateProfile(profile);
        }
    }

    
}
