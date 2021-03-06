package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Reports.Classes.Report;
import com.CSCI152.team4.server.Reports.Classes.ReportId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportsRepo extends CrudRepository<Report, ReportId> {

    @Query("select r from Report r where r.reportId.businessId = :businessId ")
    Page<Report> findAllByBusinessId(@Param("businessId") Integer businessId, Pageable page);

}
