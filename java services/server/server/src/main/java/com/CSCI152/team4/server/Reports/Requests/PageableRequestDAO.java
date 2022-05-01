package com.CSCI152.team4.server.Reports.Requests;

import com.CSCI152.team4.server.Accounts.Classes.AccountId;
import com.CSCI152.team4.server.Util.InstanceClasses.Request;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashMap;

public class PageableRequestDAO extends Request {

    /*Page is sent to Database to get the desired results*/
    Integer page;
    Integer size;
    Sort sort;
    HashMap<String, String> sortProperties;

    public PageableRequestDAO() {
    }

    public PageableRequestDAO(Integer page, Integer size, Sort sort, HashMap<String, String> sortProperties) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.sortProperties = sortProperties;
    }

    public PageableRequestDAO(String token, AccountId accountId, Integer page, Integer size, Sort sort, HashMap<String, String> sortProperties) {
        super(token, accountId);
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.sortProperties = sortProperties;
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
        for(String key : sortProperties.keySet())
            sort = sort.by(sortProperties.get(key), key).and(sort);
        return sort;
    }

    public void setSort(Sort sort) {
        if(sort == null){
            sort = Sort.by("date").descending();
        }

        if (getSortProperties() != null){
            for(String key : sortProperties.keySet())
                sort = Sort.by(sortProperties.get(key), key).and(sort);
        }
        this.sort = sort;
    }

    public HashMap<String, String> getSortProperties() {
        return sortProperties;
    }

    public void setSortProperties(HashMap<String, String> sortProperties) {

        this.sortProperties = sortProperties;
        for(String key : sortProperties.keySet())
            sort = Sort.by(Sort.Direction.fromString(sortProperties.get(key)), key).and(sort);

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
