package com.CSCI152.team4.server.Reports.Classes;

import java.util.List;

/*This class will serve as a Wrapper for an incoming
* report submission. It will house the Report Fields themselves
* as well as a list of profiles attached to the report*/
public class ReportSubmissionRequest {

    Report report;
    List<Profile> profileList;

    public ReportSubmissionRequest(Report report, List<Profile> profileList) {
        this.report = report;
        this.profileList = profileList;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public List<Profile> getProfileList() {
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}
