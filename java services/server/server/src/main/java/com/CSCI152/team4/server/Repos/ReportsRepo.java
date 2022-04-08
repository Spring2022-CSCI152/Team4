package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Reports.Classes.ReportId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepo extends CrudRepository<Report, ReportId> {
}
