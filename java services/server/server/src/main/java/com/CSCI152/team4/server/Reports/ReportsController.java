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
        System.out.println("Setting Report Format");
        settingsService.setReportFormat(request);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/set_profile_format")
    public ResponseEntity<Enum<HttpStatus>> setProfileFormat(@RequestBody ProfileFormatUpdateRequestDAO request){
        System.out.println("Setting Profile Format");
        settingsService.setProfileFormat(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/get_report_format")
    public ReportFormat getReportFormat(@RequestBody Request request){
        return settingsService.getReportFormat(request);
    }

    @PostMapping("/get_profile_format")
    public CustomerProfileFormat getProfileFormat(@RequestBody Request request){
        return settingsService.getProfileFormat(request);
    }

    @PostMapping("/get_reports")
    public Page<Report> getReports(@RequestBody PageableRequestDAO request){
        return reportsService.getReports(request);
    }

    @PostMapping("/save_report")
    public Report saveReport(@RequestBody ReportSubmissionRequestDAO request){
        return reportsService.saveReport(request);
    }

    @PutMapping("/update_profile")
    public Profile updateProfile(@RequestBody ProfileSubmissionRequestDAO request){
        return reportsService.updateProfile(request);
    }

    @PostMapping("/get_profile/{profile_id}")
    public Profile getProfile(@PathVariable("profile_id") String profileId, @RequestBody Request request){
        return reportsService.getProfile(profileId, request);
    }

    @PostMapping("/get_profiles")
    public Page<Profile> getProfiles(@RequestBody PageableRequestDAO request){
        return reportsService.getProfilesByPage(request);
    }

    @PutMapping("/update_report")
    public Report updateReport(@RequestBody ReportSubmissionRequestDAO request){
        return reportsService.updateReport(request);
    }
}
