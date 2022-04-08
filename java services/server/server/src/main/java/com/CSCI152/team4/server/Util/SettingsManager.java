package com.CSCI152.team4.server.Util;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

import java.util.List;

/*This interface is for Modifying Settings across components
*
* This will be the contract for items that create/update:
*   Report Formats
*   Profile Formats*/
public interface SettingsManager {


    ReportFormat createDefaultReportFormat();

    ReportFormat createNewReportFormat(List<String> settings);

    ReportFormat updateReportFormat(List<String> settings);

}
