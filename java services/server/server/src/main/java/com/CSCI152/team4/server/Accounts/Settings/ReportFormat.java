package com.CSCI152.team4.server.Accounts.Settings;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "report_format")
public class ReportFormat {
    @Id
    @Column(nullable = false)
    private int businessId;
    private boolean reportId;
    private boolean profiles;
    private boolean date;
    private boolean time;
    private boolean author;
    private boolean type;
    private boolean box1;
    private String box1Name;
    private boolean box2;
    private String box2Name;
    private boolean box3;
    private String box3Name;
    private boolean box4;
    private String box4Name;
    private boolean box5;
    private String box5Name;
    private boolean attachments;
    private boolean changeLog;

    //Needed for Persistence Provider
    public ReportFormat(){}

    @Override
    public String toString() {
        return "ReportFormat{" +
                "businessId=" + businessId +
                ", reportId=" + reportId +
                ", profiles=" + profiles +
                ", date=" + date +
                ", time=" + time +
                ", author=" + author +
                ", type=" + type +
                ", box1=" + box1 +
                ", box1Name='" + box1Name + '\'' +
                ", box2=" + box2 +
                ", box2Name='" + box2Name + '\'' +
                ", box3=" + box3 +
                ", box3Name='" + box3Name + '\'' +
                ", box4=" + box4 +
                ", box4Name='" + box4Name + '\'' +
                ", box5=" + box5 +
                ", box5Name='" + box5Name + '\'' +
                ", attachments=" + attachments +
                ", changeLog=" + changeLog +
                '}';
    }

    /*Getters and Setters Needed for Persistence*/
    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public boolean isReportId() {
        return reportId;
    }

    public void setReportId(boolean reportId) {
        this.reportId = reportId;
    }

    public boolean isProfiles() {
        return profiles;
    }

    public void setProfiles(boolean profiles) {
        this.profiles = profiles;
    }

    public boolean isDate() {
        return date;
    }

    public void setDate(boolean date) {
        this.date = date;
    }

    public boolean isTime() {
        return time;
    }

    public void setTime(boolean time) {
        this.time = time;
    }

    public boolean isAuthor() {
        return author;
    }

    public void setAuthor(boolean author) {
        this.author = author;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isBox1() {
        return box1;
    }

    public void setBox1(boolean box1) {
        this.box1 = box1;
    }

    public String getBox1Name() {
        return box1Name;
    }

    public void setBox1Name(String box1Name) {
        this.box1Name = box1Name;
    }

    public boolean isBox2() {
        return box2;
    }

    public void setBox2(boolean box2) {
        this.box2 = box2;
    }

    public String getBox2Name() {
        return box2Name;
    }

    public void setBox2Name(String box2Name) {
        this.box2Name = box2Name;
    }

    public boolean isBox3() {
        return box3;
    }

    public void setBox3(boolean box3) {
        this.box3 = box3;
    }

    public String getBox3Name() {
        return box3Name;
    }

    public void setBox3Name(String box3Name) {
        this.box3Name = box3Name;
    }

    public boolean isBox4() {
        return box4;
    }

    public void setBox4(boolean box4) {
        this.box4 = box4;
    }

    public String getBox4Name() {
        return box4Name;
    }

    public void setBox4Name(String box4Name) {
        this.box4Name = box4Name;
    }

    public boolean isBox5() {
        return box5;
    }

    public void setBox5(boolean box5) {
        this.box5 = box5;
    }

    public String getBox5Name() {
        return box5Name;
    }

    public void setBox5Name(String box5Name) {
        this.box5Name = box5Name;
    }

    public boolean isAttachments() {
        return attachments;
    }

    public void setAttachments(boolean attachments) {
        this.attachments = attachments;
    }

    public boolean isChangeLog() {
        return changeLog;
    }

    public void setChangeLog(boolean changeLog) {
        this.changeLog = changeLog;
    }


}
