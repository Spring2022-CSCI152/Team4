package com.CSCI152.team4.server.Accounts.Settings;

import com.CSCI152.team4.server.Reports.Classes.Address;
import com.CSCI152.team4.server.Reports.Classes.Attributes;
import com.CSCI152.team4.server.Reports.Classes.BanDuration;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "profile_format")
public class CustomerProfileFormat {

    @Id
    Integer businessId;
    private boolean name;
    private boolean status;
    private boolean address;
    private boolean banDuration;
    private boolean attributes;
    private boolean imageName;
    private boolean involvement;
    private boolean reports;

    public CustomerProfileFormat() {
    }

    public CustomerProfileFormat(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public boolean isName() {
        return name;
    }

    public void setName(boolean name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAddress() {
        return address;
    }

    public void setAddress(boolean address) {
        this.address = address;
    }

    public boolean isBanDuration() {
        return banDuration;
    }

    public void setBanDuration(boolean banDuration) {
        this.banDuration = banDuration;
    }

    public boolean isAttributes() {
        return attributes;
    }

    public void setAttributes(boolean attributes) {
        this.attributes = attributes;
    }

    public boolean isImageName() {
        return imageName;
    }

    public void setImageName(boolean imageName) {
        this.imageName = imageName;
    }

    public boolean isInvolvement() {
        return involvement;
    }

    public void setInvolvement(boolean involvement) {
        this.involvement = involvement;
    }

    public boolean isReports() {
        return reports;
    }

    public void setReports(boolean reports) {
        this.reports = reports;
    }
}
