package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.RequestDAO;

public class ReportFormatUpdateRequestDAO extends RequestDAO {

    private ReportFormat reportFormat;

    public ReportFormatUpdateRequestDAO() {
        super();
    }

    public ReportFormatUpdateRequestDAO(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    public ReportFormatUpdateRequestDAO(String token, AccountId accountId, ReportFormat reportFormat) {
        super(token, accountId);
        this.reportFormat = reportFormat;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }
}
