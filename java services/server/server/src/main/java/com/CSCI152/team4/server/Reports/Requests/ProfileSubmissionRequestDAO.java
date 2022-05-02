package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Reports.Classes.Profile;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

public class ProfileSubmissionRequestDAO extends Request {

    Profile profile;

    public ProfileSubmissionRequestDAO(){
        super();
    }

    public ProfileSubmissionRequestDAO(Profile profile) {
        this.profile = profile;
    }

    public ProfileSubmissionRequestDAO(String token, AccountId accountId, Profile profile) {
        super(token, accountId);
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
