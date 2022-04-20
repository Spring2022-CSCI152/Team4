package com.CSCI152.team4.server.Reports.Classes;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ProfileId implements Serializable {

    private static final long serialVersionUID = -7237654621153853067L;
    private Integer businessId;
    private String profileId;

    public ProfileId() {
        if(this.profileId == null){
            this.profileId = UUID.randomUUID().toString();
        }
    }

    public ProfileId(Integer businessId, String profileId) {
        this.businessId = businessId;
        this.profileId = profileId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileId)) return false;
        ProfileId profileId1 = (ProfileId) o;
        return getBusinessId().equals(profileId1.getBusinessId()) && getProfileId().equals(profileId1.getProfileId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusinessId(), getProfileId());
    }
}
