package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

public interface SettingsRepoInterface {

    ReportFormat saveReportFormat(ReportFormat reportFormat);

    CustomerProfileFormat saveCustomerProfileFormat(CustomerProfileFormat profileFormat);

    boolean reportFormatExistsById(Integer businessId);

    boolean customerProfileFormatExistsById(Integer businessId);

    ReportFormat getReportFormatIfExists(Integer businessId);

    CustomerProfileFormat getCustomerProfileIfExists(Integer businessId);


}
