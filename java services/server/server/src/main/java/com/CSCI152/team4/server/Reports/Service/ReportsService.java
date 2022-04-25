package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.Permissions;
import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.ProfileId;
import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Reports.Classes.ReportId;
import com.CSCI152.team4.server.Reports.Requests.PageableRequest;
import com.CSCI152.team4.server.Reports.Requests.ProfileSubmissionRequest;
import com.CSCI152.team4.server.Reports.Requests.ReportSubmissionRequest;
import com.CSCI152.team4.server.Reports.Validator.ReportValidator;
import com.CSCI152.team4.server.Repos.CustomerProfilesRepo;
import com.CSCI152.team4.server.Repos.ReportsRepo;
import com.CSCI152.team4.server.Util.InstanceClasses.AccountsRepoManager;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import com.CSCI152.team4.server.Util.InstanceClasses.SecurityUtil;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import com.CSCI152.team4.server.Util.Interfaces.SecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Permission;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
public class ReportsService {

    private Integer defaultPageSize ;
    private TokenAuthenticator authenticator;
    private ReportsRepo reports;
    private CustomerProfilesRepo profiles;
    private ReportValidator validator;
    private AccountsRepoManager accountsRepoManager;
    private SecurityManager securityManager;

    public ReportsService(@Value("${spring.data.web.pageable.default-page-size}") Integer defaultPageSize,
                          TokenAuthenticator authenticator,
                          ReportsRepo reports,
                          CustomerProfilesRepo profiles,
                          ReportValidator validator,
                          AccountsRepoManager accountsRepoManager,
                          SecurityUtil securityManager) {
        this.defaultPageSize = defaultPageSize;
        this.authenticator = authenticator;
        this.reports = reports;
        this.profiles = profiles;
        this.validator = validator;
        this.accountsRepoManager = accountsRepoManager;
        this.securityManager = securityManager;
    }

    public Page<Report> getReports(PageableRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        if(request.getPage() == null){
            request.setPageable(0, defaultPageSize, Sort.by("date").descending());
        }
        return reports.findAllByBusinessId(request.getBusinessId(), request.getPageable());
    }

    public void saveReport(ReportSubmissionRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        validatePermission(request.getAccountId(), Permissions.CR);

        Report reportToSave = request.getReport();

        validator.validateReport(reportToSave);
        validateProfiles(request.getProfileList());

        List<Profile> newProfiles = new ArrayList<>();

        for(Profile p : request.getProfileList()){
            /*If the profiles already exists,
            * take it out of the database to update*/
            if(profiles.existsById(p.getProfileId())){
                p = profiles.findById(p.getProfileId()).get();
            }

            //Add Report ID to profile 'reports list'
            p.appendToReports(request.getReport().getReportIdString());

            newProfiles.add(p);
            //Save Profile
            profiles.save(p);
        }

        /*Add profiles to report entity*/
        reportToSave.setProfiles(newProfiles);

        /*Save Report Entity*/
        reports.save(request.getReport());

    }

    private void validateProfiles(List<Profile> profiles) {
        for (Profile p : profiles) {
            if (p.getProfileId() == null) {
                p.setProfileIdString(UUID.randomUUID().toString());
            }
        }
        validator.validateProfiles(profiles);
    }

    public Profile getProfile(String profileIdString, Request request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        ProfileId profileId = new ProfileId(request.getBusinessId(), profileIdString);

        if(!profiles.existsById(profileId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found!" );
        }

        return profiles.findById(profileId).get();
    }

    public Page<Profile> getProfilesByPage(PageableRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        if(request.getPage() == null){
            request.setPageable(0, defaultPageSize, Sort.by("name").ascending());
        }
        return profiles.findAllByBusinessId(request.getBusinessId(), request.getPageable());
    }

    public Report updateReport(ReportSubmissionRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        validatePermission(request.getAccountId(), Permissions.ER);

        ReportId reportId = request.getReport().getReportId();

        if(!reports.existsById(reportId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Original Report not found!");
        }
        if(!reportId.getBusinessId().equals(request.getBusinessId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Business ID!");
        }

        Report newReport = request.getReport();
        newReport.updateChangeLog(newReport.getAuthor(), Timestamp.valueOf(now()));

        newReport = reports.save(newReport);

        return newReport;
    }


    public Profile updateProfile(ProfileSubmissionRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());
        validatePermission(request.getAccountId(), Permissions.EP);

        ProfileId profileId = request.getProfile().getProfileId();

        if(!profiles.existsById(profileId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile Not Found!");
        }

        Profile updatedProfile = request.getProfile();

        return profiles.save(updatedProfile);

    }

    private void validatePermission(AccountId accountId, Permissions permission){
        if(!isPermitted(accountId, permission)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not Allowed!");
        }
    }

    private boolean isPermitted(AccountId accountId, Permissions permission){
        return accountsRepoManager.adminExists(accountId) ||
                accountsRepoManager.getEmployeeIfExists(accountId)
                        .getPermissionsList().contains(permission.name());
    }
}
