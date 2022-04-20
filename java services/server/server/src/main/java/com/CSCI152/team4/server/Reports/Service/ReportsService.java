package com.CSCI152.team4.server.Reports.Service;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Reports.Requests.PageableRequest;
import com.CSCI152.team4.server.Reports.Requests.ReportSubmissionRequest;
import com.CSCI152.team4.server.Reports.Validator.ReportValidator;
import com.CSCI152.team4.server.Repos.CustomerProfileRepo;
import com.CSCI152.team4.server.Repos.ReportsRepo;
import com.CSCI152.team4.server.Util.InstanceClasses.TokenAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReportsService {

    private Integer defaultPageSize ;
    private TokenAuthenticator authenticator;
    private ReportsRepo reports;
    private CustomerProfileRepo profiles;
    private ReportValidator validator;

    @Autowired
    public ReportsService(TokenAuthenticator authenticator, ReportsRepo reports,
                          @Value("${spring.data.web.pageable.default-page-size}") Integer defaultPageSize,
                          CustomerProfileRepo profiles, ReportValidator validator) {
        this.authenticator = authenticator;
        this.reports = reports;
        this.defaultPageSize = defaultPageSize;
        this.profiles = profiles;
        this.validator = validator;
    }

    public Page<Report> getReports(PageableRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        if(request.getPage() == null){
            request.setPage(PageRequest.of(0, defaultPageSize));
        }
        return reports.findByBusinessId(request.getBusinessId(), request.getPage());
    }

    public void saveReport(ReportSubmissionRequest request){
        authenticator.validateToken(request.getToken(), request.getAccountIdString());

        Report reportToSave = request.getReport();
        //Generate report ID
        if(reportToSave.getReportIdString() == null){
            reportToSave.setReportIdString(UUID.randomUUID().toString());
        }

        List<Profile> newProfiles = new ArrayList<>();

        for(Profile p : request.getProfileList()){
            if(p.getProfileIdString() == null){
                //If not a known Profile, give a new Id
                p.getProfileId().setProfileId(UUID.randomUUID().toString());
            }
            else{
                //else, retrieve old entity
                p = profiles.findById(p.getProfileId()).get();
            }
            //Add Report ID to profile 'reports list'
            p.getReports().add(request.getReport().getReportIdString());
            newProfiles.add(p);
            //Save Profile
            profiles.save(p);
        }

        reportToSave.setProfiles(newProfiles);

        reports.save(request.getReport());

    }
}
