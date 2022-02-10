package com.CSCI152.team4.server.Accounts;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "report_format")
public class ReportFormat {
    @Id
    @NotNull(message = "Business Id must not be empty")
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

    public ReportFormat(ReportFormatBuilder builder){
        this.businessId = builder.businessId;
        this.reportId = builder.reportId;
        this.profiles = builder.profiles;
        this.date = builder.date;
        this.time = builder.time;
        this.author = builder.author;
        this.type = builder.type;
        this.box1 = builder.box1;
        this.box1Name = builder.box1Name;
        this.box2 = builder.box2;
        this.box2Name = builder.box2Name;
        this.box3 = builder.box3;
        this.box3Name = builder.box3Name;
        this.box4 = builder.box4;
        this.box4Name = builder.box4Name;
        this.box5 = builder.box5;
        this.box5Name = builder.box5Name;
        this.attachments = builder.attachments;
        this.changeLog = builder.changeLog;
    }

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

    public static class ReportFormatBuilder {
        private int businessId;
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

        ReportFormatBuilder(int businessId){
            this.businessId = businessId;
        }
        public ReportFormatBuilder enableReportId(){
            this.reportId = true;
            return this;
        }
        public ReportFormatBuilder enableProfiles(){
            this.profiles = true;
            return this;
        }
        public ReportFormatBuilder enableDate(){
            this.date = true;
            return this;
        }
        public ReportFormatBuilder enableTime(){
            this.time = true;
            return this;
        }
        public ReportFormatBuilder enableAuthor(){
            this.author = true;
            return this;
        }
        public ReportFormatBuilder enableType(){
            this.type = true;
            return this;
        }
        public ReportFormatBuilder enableBox1(){
            this.box1 = true;
            return this;
        }
        public ReportFormatBuilder nameBox1(String name){
            this.box1Name = name;
            return this;
        }
        public ReportFormatBuilder enableBox2(){
            this.box2 = true;
            return this;
        }
        public ReportFormatBuilder nameBox2(String name){
            this.box2Name = name;
            return this;
        }
        public ReportFormatBuilder enableBox3(){
            this.box3 = true;
            return this;
        }
        public ReportFormatBuilder nameBox3(String name){
            this.box3Name = name;
            return this;
        }
        public ReportFormatBuilder enableBox4(){
            this.box4 = true;
            return this;
        }
        public ReportFormatBuilder nameBox4(String name){
            this.box4Name = name;
            return this;
        }
        public ReportFormatBuilder enableBox5(){
            this.box5 = true;
            return this;
        }
        public ReportFormatBuilder nameBox5(String name){
            this.box5Name = name;
            return this;
        }
        public ReportFormatBuilder enableAttachments(){
            this.attachments = true;
            return this;
        }
        public ReportFormatBuilder enableChangeLog(){
            this.changeLog = true;
            return this;
        }

        public ReportFormat build(){
            return new ReportFormat(this);
        }

    }
}
