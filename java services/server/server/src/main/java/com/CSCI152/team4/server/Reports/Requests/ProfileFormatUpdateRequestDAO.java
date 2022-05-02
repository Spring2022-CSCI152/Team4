package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

import java.util.Objects;

public class ProfileFormatUpdateRequestDAO extends Request {

    CustomerProfileFormat profileFormat;

    public ProfileFormatUpdateRequestDAO() {
    }

    public ProfileFormatUpdateRequestDAO(CustomerProfileFormat profileFormat) {
        this.profileFormat = profileFormat;
    }

    public ProfileFormatUpdateRequestDAO(String token, AccountId accountId, CustomerProfileFormat profileFormat) {
        super(token, accountId);
        this.profileFormat = profileFormat;
    }

    public CustomerProfileFormat getProfileFormat() {
        return profileFormat;
    }

    public void setProfileFormat(CustomerProfileFormat profileFormat) {
        this.profileFormat = profileFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileFormatUpdateRequestDAO)) return false;
        ProfileFormatUpdateRequestDAO that = (ProfileFormatUpdateRequestDAO) o;
        return getProfileFormat().equals(that.getProfileFormat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileFormat());
    }
}
