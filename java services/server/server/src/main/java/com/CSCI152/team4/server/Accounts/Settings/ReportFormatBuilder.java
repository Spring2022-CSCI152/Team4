package com.CSCI152.team4.server.Accounts.Settings;


public class ReportFormatBuilder {
    private Integer businessId;
    private boolean reportId = false;
    private boolean profiles = false;
    private boolean date = false;
    private boolean time = false;
    private boolean author = false;
    private boolean type = false;
    private boolean box1;
    private String box1Name = "";
    private boolean box2;
    private String box2Name = "";
    private boolean box3;
    private String box3Name = "";
    private boolean box4;
    private String box4Name = "";
    private boolean box5;
    private String box5Name = "";
    private boolean attachments = false;
    private boolean changeLog = false;

    public ReportFormatBuilder(Integer businessId) {
        this.businessId = businessId;
    }

    public ReportFormatBuilder enableReportId() {
        this.reportId = true;
        return this;
    }

    public ReportFormatBuilder enableProfiles() {
        this.profiles = true;
        return this;
    }

    public ReportFormatBuilder enableDate() {
        this.date = true;
        return this;
    }

    public ReportFormatBuilder enableTime() {
        this.time = true;
        return this;
    }

    public ReportFormatBuilder enableAuthor() {
        this.author = true;
        return this;
    }

    public ReportFormatBuilder enableType() {
        this.type = true;
        return this;
    }

    public ReportFormatBuilder enableBox1() {
        this.box1 = true;
        return this;
    }

    public ReportFormatBuilder nameBox1(String name) {
        this.box1Name = name;
        return this;
    }

    public ReportFormatBuilder enableBox2() {
        this.box2 = true;
        return this;
    }

    public ReportFormatBuilder nameBox2(String name) {
        this.box2Name = name;
        return this;
    }

    public ReportFormatBuilder enableBox3() {
        this.box3 = true;
        return this;
    }

    public ReportFormatBuilder nameBox3(String name) {
        this.box3Name = name;
        return this;
    }

    public ReportFormatBuilder enableBox4() {
        this.box4 = true;
        return this;
    }

    public ReportFormatBuilder nameBox4(String name) {
        this.box4Name = name;
        return this;
    }

    public ReportFormatBuilder enableBox5() {
        this.box5 = true;
        return this;
    }

    public ReportFormatBuilder nameBox5(String name) {
        this.box5Name = name;
        return this;
    }

    public ReportFormatBuilder enableAttachments() {
        this.attachments = true;
        return this;
    }

    public ReportFormatBuilder enableChangeLog() {
        this.changeLog = true;
        return this;
    }

    public ReportFormat build() {
        ReportFormat reportFormat = new ReportFormat();
        reportFormat.setBusinessId(this.businessId);
        reportFormat.setReportId(this.reportId);
        reportFormat.setProfiles(this.profiles);
        reportFormat.setDate(this.date);
        reportFormat.setTime(this.time);
        reportFormat.setAuthor(this.author);
        reportFormat.setType(this.type);
        reportFormat.setBox1(this.box1);
        reportFormat.setBox1Name(this.box1Name);
        reportFormat.setBox2(this.box2);
        reportFormat.setBox2Name(this.box2Name);
        reportFormat.setBox3(this.box3);
        reportFormat.setBox3Name(this.box3Name);
        reportFormat.setBox4(this.box4);
        reportFormat.setBox4Name(this.box4Name);
        reportFormat.setBox5(this.box5);
        reportFormat.setBox5Name(this.box5Name);
        reportFormat.setAttachments(this.attachments);
        reportFormat.setChangeLog(this.changeLog);

        return reportFormat;
    }

}

