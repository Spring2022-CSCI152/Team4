package com.CSCI152.team4.server.Reports;

import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import com.CSCI152.team4.server.Reports.Requests.ProfileFormatUpdateRequest;
import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequest;
import com.CSCI152.team4.server.Reports.Service.ReportsService;
import com.CSCI152.team4.server.Reports.Service.SettingsService;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Enum<HttpStatus>> setReportFormat(@RequestBody ProfileFormatUpdateRequest request){

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
}
