package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

import java.util.ArrayList;
import java.util.List;

/*This class will serve as a Wrapper for an incoming
* report submission. It will house the Report Fields themselves
* as well as a list of profiles attached to the report*/
public class ReportSubmissionRequest extends Request {

    Report report;
    List<Profile> profileList;

    public ReportSubmissionRequest() {
    }

    public ReportSubmissionRequest(Report report, List<Profile> profileList) {
        this.report = report;
        this.profileList = profileList;
    }

    public ReportSubmissionRequest(String token, AccountId accountId, Report report, List<Profile> profileList) {
        super(token, accountId);
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
        if(profileList == null){
            profileList = new ArrayList<>();
        }
        return profileList;
    }

    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }
}
