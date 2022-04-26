package com.CSCI152.team4.server.Reports.Classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profiles")
public class Profile {

    @EmbeddedId
    private ProfileId profileId;
    private String name;
    private String status;
    @Embedded
    private Address address;
    @Embedded
    private BanDuration banDuration;
    @Embedded
    private Attributes attributes;

    private String imageName;
    private String involvement;

    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private List<String> reports;

    public Profile() {
    }

    public Profile(ProfileId profileId,
                   String name, String status,
                   Address address, BanDuration banDuration,
                   Attributes attributes, String imageName,
                   String involvement, List<String> reports) {
        this.profileId = profileId;
        this.name = name;
        this.status = status;
        this.address = address;
        this.banDuration = banDuration;
        this.attributes = attributes;
        this.imageName = imageName;
        this.involvement = involvement;
        this.reports = reports;
    }

    public Profile(Integer businessId,
                   String name, String status,
                   Address address, BanDuration banDuration,
                   Attributes attributes, String imageName,
                   String involvement, List<String> reports) {
        this.generateNewProfileId(businessId);
        this.name = name;
        this.status = status;
        this.address = address;
        this.banDuration = banDuration;
        this.attributes = attributes;
        this.imageName = imageName;
        this.involvement = involvement;
        this.reports = reports;
    }

    public Profile(Integer businessId,
                   String name, String status,
                   Address address, BanDuration banDuration,
                   Attributes attributes, String imageName,
                   String involvement, String reportId) {
        this.generateNewProfileId(businessId);
        this.name = name;
        this.status = status;
        this.address = address;
        this.banDuration = banDuration;
        this.attributes = attributes;
        this.imageName = imageName;
        this.involvement = involvement;
        this.appendToReports(reportId);
    }

    private void generateNewProfileId(Integer businessId){
        this.profileId = new ProfileId(businessId, UUID.randomUUID().toString());
    }
    public ProfileId getProfileId() {
        return profileId;
    }

    public Integer getBusinessId(){
        return this.profileId.getBusinessId();
    }

    public void setBusinessId(Integer businessId){
        this.profileId.setBusinessId(businessId);
    }

    public String getProfileIdString(){
        return this.profileId.getProfileId();
    }

    public void setProfileIdString(String profileIdString){
        this.profileId.setProfileId(profileIdString);
    }
    public void setProfileId(ProfileId profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public BanDuration getBanDuration() {
        return banDuration;
    }

    public void setBanDuration(BanDuration banDuration) {
        this.banDuration = banDuration;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getInvolvement() {
        return involvement;
    }

    public void setInvolvement(String involvement) {
        this.involvement = involvement;
    }

    public List<String> getReports() {
        if(reports == null){
            reports = new ArrayList<>();
        }
        return reports;
    }

    public void setReports(List<String> reports) {
        this.reports = reports;
    }

    public void appendToReports(String reportId){
        if(this.reports == null){
            this.reports = new ArrayList<String>();
        }
        this.reports.add(reportId);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "profileId=" + profileId +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", address=" + address +
                ", banDuration=" + banDuration +
                ", attributes=" + attributes +
                ", imageName='" + imageName + '\'' +
                ", involvement='" + involvement + '\'' +
                ", reports=" + reports +
                '}';
    }
}
