package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.ProfileId;
import com.CSCI152.team4.server.Reports.Classes.Report;

import java.util.List;

public interface ReportsRepoInterface {

    public Report saveReport(Report report);

    public Profile saveProfile(Profile profile);

    public Report saveReportAndProfiles(Report report, List<Profile> profiles);

    public Report getReportById(ProfileId profileId);

    public List<Report> getReportsByBusinessId(Integer businessId);
}
