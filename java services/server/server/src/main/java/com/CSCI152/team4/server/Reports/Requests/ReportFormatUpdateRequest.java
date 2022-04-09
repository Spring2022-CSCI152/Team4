package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;

public class ReportFormatUpdateRequest {
    private String token;
    private String requestingAccountId;
    private Integer businessId;
    private ReportFormat reportFormat;

    public ReportFormatUpdateRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestingAccountId() {
        return requestingAccountId;
    }

    public void setRequestingAccountId(String requestingAccountId) {
        this.requestingAccountId = requestingAccountId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public ReportFormat getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(ReportFormat reportFormat) {
        this.reportFormat = reportFormat;
    }
}
