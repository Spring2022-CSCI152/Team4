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
    public ResponseEntity<Enum<HttpStatus>> setReportFormat(@RequestBody ReportFormatUpdateRequest request){
        settingsService.setReportFormat(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/set_profile_format")
    public ResponseEntity<Enum<HttpStatus>> setProfileFormat(@RequestBody ProfileFormatUpdateRequest request){
        settingsService.setProfileFormat(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get_report_format")
    public ReportFormat getReportFormat(@RequestBody Request request){
        return settingsService.getReportFormat(request);
    }

    @GetMapping("/get_profile_format")
    public CustomerProfileFormat getProfileFormat(@RequestBody Request request){
        return settingsService.getProfileFormat(request);
    }

    @GetMapping("/get_reports")
    public Page<Report> getReports(@RequestBody PageableRequest request){
        return reportsService.getReports(request);
    }

    @PostMapping("/save_report")
    public Report saveReport(@RequestBody ReportSubmissionRequest request){
        return reportsService.saveReport(request);
    }

    @PutMapping("/update_profile")
    public Profile updateProfile(@RequestBody ProfileSubmissionRequest request){
        return reportsService.updateProfile(request);
    }

    @GetMapping("/get_profile/{profile_id}")
    public Profile getProfile(@PathVariable("profile_id") String profileId, @RequestBody Request request ){
        return reportsService.getProfile(profileId, request);
    }

    @GetMapping("/get_profiles")
    public Page<Profile> getProfiles(@RequestBody PageableRequest request){
        return reportsService.getProfilesByPage(request);
    }

    @PutMapping("/update_report")
    public Report updateReport(@RequestBody ReportSubmissionRequest request){
        return reportsService.updateReport(request);
    }
}
