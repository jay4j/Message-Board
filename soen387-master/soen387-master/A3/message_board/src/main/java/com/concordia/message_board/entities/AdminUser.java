package com.concordia.message_board.entities;

import java.util.ArrayList;
import java.util.List;

public class AdminUser {
    //store Admin users list
    private List<String> adminNamesList;

    public AdminUser() {
        adminNamesList = new ArrayList<>();
    }

    public void add(String name){
        adminNamesList.add(name);
    }

    public List<String> getAdminNamesList() {
        return adminNamesList;
    }

    public void setAdminNamesList(List<String> adminNamesList) {
        this.adminNamesList = adminNamesList;
    }

    @Override
    public String toString() {
        String li = "";
        for(String str:adminNamesList){
            li += " "+str;
        }
        return li;
    }
}
