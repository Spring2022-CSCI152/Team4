package com.CSCI152.team4.server.Reports.Classes;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report {

    /*
    * get and set formats
    *
    * get and submit reports
    *   Upon report submission extract list of profiles attached and/or
    *   create report submission request that contains a list of profiles
    * get profiles*/
    @EmbeddedId
    private ReportId reportId;

    //Profile Ids
    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private List<String> profiles;
    //Submission Time
    private Timestamp date;
    //A String representation of the time that report occurred
    private String time;
    private String author;
    private String type;
    @Lob
    private String box1;
    @Lob
    private String box2;
    @Lob
    private String box3;
    @Lob
    private String box4;
    @Lob
    private String box5;

    //Attachments will be defined later, and may
    //store references to all attachments in another table
    //or a list of attachment IDs
//    private boolean attachments;

    /*
    * Change Log will consist of a list of Author Names and Timestamps
    * Each Entry will be:
    * Author: %s, Timestamp %s.format(report.author, report.timestamp.toString())*/
    @Column(nullable = false)
    @ElementCollection(targetClass = String.class, fetch = FetchType.LAZY)
    private List<String> changeLog;

    public Report() {
    }

    public ReportId getReportId() {
        return reportId;
    }

    public void setReportId(ReportId reportId) {
        this.reportId = reportId;
    }

    public Integer getBusinessId() {
        return reportId.getBusinessId();
    }

    public void setBusinessId(Integer businessId) {
        this.reportId.setBusinessId(businessId);
    }

    public String getReportIdString() {
        return reportId.getReportId();
    }

    public void setReportIdString(String reportId) {
        this.reportId.setReportId(reportId);
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBox1() {
        return box1;
    }

    public void setBox1(String box1) {
        this.box1 = box1;
    }

    public String getBox2() {
        return box2;
    }

    public void setBox2(String box2) {
        this.box2 = box2;
    }

    public String getBox3() {
        return box3;
    }

    public void setBox3(String box3) {
        this.box3 = box3;
    }

    public String getBox4() {
        return box4;
    }

    public void setBox4(String box4) {
        this.box4 = box4;
    }

    public String getBox5() {
        return box5;
    }

    public void setBox5(String box5) {
        this.box5 = box5;
    }

    public List<String> getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(List<String> changeLog) {
        this.changeLog = changeLog;
    }

    public void updateChangeLog(String author, Timestamp timestamp){
        this.changeLog.add(String.format("Timestamp: %s, Author: %s", timestamp.toString(), author));
    }
}
