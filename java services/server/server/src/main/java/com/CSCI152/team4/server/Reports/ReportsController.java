package com.CSCI152.team4.server.Reports;

import com.CSCI152.team4.server.Reports.Requests.ReportFormatUpdateRequest;
import com.CSCI152.team4.server.Reports.Service.ReportsService;
import com.CSCI152.team4.server.Reports.Service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
