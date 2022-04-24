package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.ProfileId;
import com.CSCI152.team4.server.Reports.Classes.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfilesRepo extends CrudRepository<Profile, ProfileId> {

    @Query("select p from Profile r where p.profileId.businessId = :businessId ")
    Page<Profile> findAllByBusinessId(@Param("businessId") Integer businessId, Pageable page);

}
