package com.CSCI152.team4.server.Util.Interfaces;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.ProfileId;
import com.CSCI152.team4.server.Reports.Classes.Report;

import java.util.List;

public interface ReportsRepoInterface {

    Report saveReport(Report report);

    Profile saveProfile(Profile profile);

    Report saveReportAndProfiles(Report report, List<Profile> profiles);

    Report getReportById(ProfileId profileId);

    List<Report> getReportsByBusinessId(Integer businessId);
}
