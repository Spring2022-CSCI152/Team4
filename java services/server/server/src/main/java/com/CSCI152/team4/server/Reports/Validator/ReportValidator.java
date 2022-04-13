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

        report.setProfiles((reportFormat.isProfiles()) ? report.getProfiles() : null);
        report.setDate((reportFormat.isDate()) ? report.getDate() : null);
        report.setTime((reportFormat.isTime()) ? report.getTime() : null);
        report.setAuthor((reportFormat.isAuthor()) ? report.getAuthor() : null);
        report.setType((reportFormat.isType()) ? report.getType() : null);
        report.setBox1((reportFormat.isBox1()) ? report.getBox1() : null);
        report.setBox2((reportFormat.isBox2()) ? report.getBox2() : null);
        report.setBox3((reportFormat.isBox3()) ? report.getBox3() : null);
        report.setBox4((reportFormat.isBox4()) ? report.getBox4() : null);
        report.setBox5((reportFormat.isBox5()) ? report.getBox5() : null);
    }

    public void validateProfile(Profile profile){
        if(!settingsRepoManager.reportFormatExistsById(profile.getBusinessId())){
            throw new ResponseStatusException(HttpStatus.OK, "Business Not Found!");
        }
        CustomerProfileFormat profileFormat =
                settingsRepoManager.getCustomerProfileIfExists(profile.getBusinessId());

        profile.setName((profileFormat.isName()) ? profile.getName() : null);
        profile.setStatus((profileFormat.isStatus()) ? profile.getStatus() : null);
        profile.setAddress((profileFormat.isAddress()) ? profile.getAddress() : null);
        profile.setBanDuration((profileFormat.isBanDuration()) ? profile.getBanDuration() : null);
        profile.setAttributes((profileFormat.isAttributes()) ? profile.getAttributes() : null);
        profile.setImageName((profileFormat.isImageName()) ? profile.getImageName() : null);
        profile.setInvolvement((profileFormat.isInvolvement()) ? profile.getInvolvement() : null);
        profile.setReports((profileFormat.isReports()) ? profile.getReports() : null);

    }

    public void validateProfiles(List<Profile> profileList){

        for(Profile profile : profileList){
            validateProfile(profile);
        }
    }


}