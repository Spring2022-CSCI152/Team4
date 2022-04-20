package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageableRequest extends Request {

    /*Page is sent to Database to get the desired results*/
    Integer page;
    Integer size;
    Sort sort;

    public PageableRequest() {
    }

    public PageableRequest(Integer page, Integer size, Sort sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public PageableRequest(String token, AccountId accountId, Integer page, Integer size, Sort sort) {
        super(token, accountId);
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        if(sort == null){
            sort = Sort.by("date").descending();
        }
        this.sort = sort;
    }

    public Pageable getPageable(){
        return PageRequest.of(page, size, sort);
    }

    public void setPageable(Integer page, Integer size, Sort sort){
        this.page = page;
        this.size = size;
        this.sort = sort;
    }
}
