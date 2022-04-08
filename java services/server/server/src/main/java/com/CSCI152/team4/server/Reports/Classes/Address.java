package com.CSCI152.team4.server.Reports.Classes;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String street1;
    private String stree2;
    private String city;
    private String state;
    private Integer zipCode;

    public Address() {
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStree2() {
        return stree2;
    }

    public void setStree2(String stree2) {
        this.stree2 = stree2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}
