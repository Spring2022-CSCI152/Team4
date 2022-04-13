package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class ReportFormatUpdateRequest extends Request {

    private ReportFormat reportFormat;

    public ReportFormatUpdateRequest() {
        super();
    }

    public ReportFormatUpdateRequest(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }

    public ReportFormatUpdateRequest(String token, AccountId accountId, ReportFormat reportFormat) {
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
