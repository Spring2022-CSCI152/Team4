package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

import java.util.List;

/*This interface is for Modifying Settings across components
*
* This will be the contract for items that create/update:
*   Report Formats
*   Profile Formats*/
public interface SettingsManagerInterface {


    ReportFormat createDefaultReportFormat(Integer businessId);

    ReportFormat createNewReportFormat(Integer businessId, List<String> settings);

    ReportFormat updateReportFormat(List<String> settings);

    CustomerProfileFormat createDefaultCustomerProfileFormat(Integer businessId);

    CustomerProfileFormat createNewCustomerProfileFormat(Integer businessId, List<String> settings);

    CustomerProfileFormat updateCustomerProfileFormat(Integer businessId, List<String> settings);



}
