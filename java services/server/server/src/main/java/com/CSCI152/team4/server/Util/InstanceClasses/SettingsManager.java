package com.CSCI152.team4.server.Util.InstanceClasses;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.Builders.ReportFormatBuilder;
import com.CSCI152.team4.server.Util.Interfaces.SettingsManagerInterface;

import java.util.List;

public class SettingsManager implements SettingsManagerInterface {
    @Override
    public ReportFormat createDefaultReportFormat(Integer businessId) {
        return new ReportFormatBuilder(businessId).build();
    }

    @Override
    public ReportFormat createNewReportFormat(Integer businessId, List<String> settings) {
        return null;
    }

    @Override
    public ReportFormat updateReportFormat(List<String> settings) {
        return null;
    }

    @Override
    public CustomerProfileFormat createDefaultCustomerProfileFormat(Integer businessId) {
        return null;
    }

    @Override
    public CustomerProfileFormat createNewCustomerProfileFormat(Integer businessId, List<String> settings) {
        return null;
    }

    @Override
    public CustomerProfileFormat updateCustomerProfileFormat(Integer businessId, List<String> settings) {
        return null;
    }
}
