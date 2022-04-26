package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Accounts.Settings.CustomerProfileFormat;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;

import java.util.Objects;

public class ProfileFormatUpdateRequest extends Request {

    CustomerProfileFormat profileFormat;

    public ProfileFormatUpdateRequest() {
    }

    public ProfileFormatUpdateRequest(CustomerProfileFormat profileFormat) {
        this.profileFormat = profileFormat;
    }

    public ProfileFormatUpdateRequest(String token, AccountId accountId, CustomerProfileFormat profileFormat) {
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
        if (!(o instanceof ProfileFormatUpdateRequest)) return false;
        ProfileFormatUpdateRequest that = (ProfileFormatUpdateRequest) o;
        return getProfileFormat().equals(that.getProfileFormat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProfileFormat());
    }
}
