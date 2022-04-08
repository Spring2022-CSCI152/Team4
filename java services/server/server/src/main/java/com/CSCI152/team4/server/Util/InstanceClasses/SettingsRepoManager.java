package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Repos.CustomerProfileFormatRepo;
import com.CSCI152.team4.server.Repos.ReportFormatRepo;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        return null;
    }

    @Override
    public CustomerProfileFormat saveCustomerProfileFormat(CustomerProfileFormat profileFormat) {
        return null;
    }

    @Override
    public boolean reportFormatExistsById(Integer businessId) {
        return false;
    }

    @Override
    public boolean customerProfileFormatExistsById(Integer businessId) {
        return false;
    }

    @Override
    public ReportFormat getReportFormatIfExists(Integer businessId) {
        return null;
    }

    @Override
    public CustomerProfileFormat getCustomerProfileIfExists(Integer businessId) {
        return null;
    }
}
