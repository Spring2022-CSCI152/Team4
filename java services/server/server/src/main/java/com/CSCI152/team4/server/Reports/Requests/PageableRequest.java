package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.data.domain.Pageable;

public class PageableRequest extends Request {

    /*Page is sent to Database to get the desired results*/
    Pageable page;

    public PageableRequest() {
    }

    public PageableRequest(Pageable page) {
        this.page = page;
    }

    public PageableRequest(String token, AccountId accountId, Pageable page) {
        super(token, accountId);
        this.page = page;
    }

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }
}
