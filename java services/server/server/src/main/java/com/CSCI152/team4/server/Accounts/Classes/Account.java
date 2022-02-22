package com.CSCI152.team4.server.Accounts.Classes;



public class Account {


    private int businessId;

    private void setBusinessId(int id) {
        this.businessId = id;
    }
    public Account(){}
    public Account(int id){
        this.setBusinessId(id);
    }

    public int getBusinessId(){
        return this.businessId;
    }
}
