package com.CSCI152.team4.server.Reports.Classes;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class BanDuration {

    @Temporal(value = TemporalType.DATE)
    private Date start;
    @Temporal(value = TemporalType.DATE)
    private Date end;

    public BanDuration() {
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
