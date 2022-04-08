package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.Interfaces.SettingsRepoInterface;

public class SettingsRepoManager implements SettingsRepoInterface {

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
