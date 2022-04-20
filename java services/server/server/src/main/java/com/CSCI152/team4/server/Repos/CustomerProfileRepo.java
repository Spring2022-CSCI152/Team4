package com.CSCI152.team4.server.Repos;

import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Reports.Classes.ProfileId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerProfileRepo extends CrudRepository<Profile, ProfileId> {
}
