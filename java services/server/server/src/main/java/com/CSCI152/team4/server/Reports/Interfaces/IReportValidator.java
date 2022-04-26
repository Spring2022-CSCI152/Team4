package com.CSCI152.team4.server.Reports.Interfaces;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.Report;

import java.util.List;

public interface IReportValidator {
    void validateReport(Report report);

    void validateProfile(Profile profile);

    void validateProfiles(List<Profile> profileList);
}
