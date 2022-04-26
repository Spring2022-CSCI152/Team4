package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Repos.CustomerProfileFormatRepo;
import com.CSCI152.team4.server.Repos.ReportFormatRepo;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Component
public class SettingsRepoManager implements SettingsRepoInterface {

    private final ReportFormatRepo reportFormats;
    private final CustomerProfileFormatRepo profileFormats;

    @Autowired
    public SettingsRepoManager(ReportFormatRepo reportFormats, CustomerProfileFormatRepo profileFormats) {
        this.reportFormats = reportFormats;
        this.profileFormats = profileFormats;
    }

    @Override
    public ReportFormat saveReportFormat(ReportFormat reportFormat) {
        return reportFormats.save(reportFormat);
    }

    @Override
    public CustomerProfileFormat saveCustomerProfileFormat(CustomerProfileFormat profileFormat) {
        return profileFormats.save(profileFormat);
    }

    @Override
    public boolean reportFormatExistsById(Integer businessId) {
        return reportFormats.existsById(businessId);
    }

    @Override
    public boolean customerProfileFormatExistsById(Integer businessId) {
        return profileFormats.existsById(businessId);
    }

    @Override
    public ReportFormat getReportFormatIfExists(Integer businessId) {
        Optional<ReportFormat> optional = reportFormats.findById(businessId);

        if(optional.isPresent()){
            return optional.get();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Report Format Not Found!");
    }

    @Override
    public CustomerProfileFormat getCustomerProfileFormatIfExists(Integer businessId) {
        Optional<CustomerProfileFormat> optional = profileFormats.findById(businessId);

        if(optional.isPresent()){
            return optional.get();
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile Format Not Found!");
    }
}
