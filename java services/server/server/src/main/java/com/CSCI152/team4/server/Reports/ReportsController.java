package com.CSCI152.team4.server.Reports;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Reports.Requests.*;
import com.CSCI152.team4.server.Reports.Service.ReportsService;
import com.CSCI152.team4.server.Reports.Service.SettingsService;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/reports")
public class ReportsController {

    private final ReportsService reportsService;
    private final SettingsService settingsService;

    @Autowired
    public ReportsController(ReportsService service, ReportsService reportsService, SettingsService settingsService) {
        this.reportsService = reportsService;
        this.settingsService = settingsService;
    }

    @PostMapping("/set_report_format")
    public ResponseEntity<Enum<HttpStatus>> setReportFormat(@RequestBody ReportFormatUpdateRequestDAO request){
        System.out.println("Setting Report Format: " + request.getAccountEmail());
        settingsService.setReportFormat(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/set_profile_format")
    public ResponseEntity<Enum<HttpStatus>> setProfileFormat(@RequestBody ProfileFormatUpdateRequestDAO request){
        System.out.println("Setting Profile Format: " + request.getAccountEmail());
        settingsService.setProfileFormat(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get_report_format")
    public ReportFormat getReportFormat(@RequestBody Request request){
        System.out.println("Getting Report Format: " + request.getAccountEmail());
        return settingsService.getReportFormat(request);
    }

    @PostMapping("/get_profile_format")
    public CustomerProfileFormat getProfileFormat(@RequestBody Request request){
        System.out.println("Getting Profile Format: " + request.getAccountEmail());
        return settingsService.getProfileFormat(request);
    }

    @PostMapping("/get_reports")
    public Page<Report> getReports(@RequestBody PageableRequestDAO request){
        System.out.println("Getting Reports: " + request.getAccountEmail());
        return reportsService.getReports(request);
    }

    @PostMapping("/save_report")
    public Report saveReport(@RequestBody ReportSubmissionRequestDAO request){
        System.out.println("Saving Reports: " + request.getAccountEmail());
        return reportsService.saveReport(request);
    }

    @PutMapping("/update_profile")
    public Profile updateProfile(@RequestBody ProfileSubmissionRequestDAO request){
        System.out.println("Updating Profile: " + request.getAccountEmail());
        return reportsService.updateProfile(request);
    }

    @PostMapping("/get_profile/{profile_id}")
    public Profile getProfile(@PathVariable("profile_id") String profileId, @RequestBody Request request){
        System.out.println("Getting Profile: " + request.getAccountEmail());
        return reportsService.getProfile(profileId, request);
    }

    @PostMapping("/get_profiles")
    public Page<Profile> getProfiles(@RequestBody PageableRequestDAO request){
        System.out.println("Getting Profiles: " + request.getAccountEmail());
        return reportsService.getProfilesByPage(request);
    }

    @PutMapping("/update_report")
    public Report updateReport(@RequestBody ReportSubmissionRequestDAO request){
        System.out.println("Updating Report: " + request.getAccountEmail());
        return reportsService.updateReport(request);
    }
}
