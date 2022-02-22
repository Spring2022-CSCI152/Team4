package com.CSCI152.team4.server.Accounts.Repos;

import com.CSCI152.team4.server.Accounts.Settings.ReportFormat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportFormatRepo extends CrudRepository<ReportFormat, Integer> {
}
