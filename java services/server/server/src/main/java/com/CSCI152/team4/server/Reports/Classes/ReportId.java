package com.CSCI152.team4.server.Reports.Classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ReportId implements Serializable {

    private static final long serialVersionUID = -7246019074116748644L;
    private Integer businessId;
    private String reportId;

    public ReportId() {
        if(this.reportId == null){
            this.reportId = UUID.randomUUID().toString();
        }
    }

    public ReportId(Integer businessId, String reportId) {
        this.businessId = businessId;
        this.reportId = reportId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReportId)) return false;
        ReportId reportId1 = (ReportId) o;
        return businessId.equals(reportId1.businessId) && reportId.equals(reportId1.reportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId, reportId);
    }
}
