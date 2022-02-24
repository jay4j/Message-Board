package com.concordia.message_board.service;

import com.concordia.message_board.entities.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFactory {
    private static Map<String,String> map = new HashMap<>();
    private static AdminUser adminUser;
    private static ConcordiaUser concordiaUser;
    private static EncsUser encsUser;
    private static CompUser compUser;
    private static SoenUser soenUser;

    public static List<String> getSpecificList(String status){
        if(status.equals("admins")) return adminUser.getAdminNamesList();
        else if (status.equals("concordia")) return concordiaUser.getConNamesList();
        else if (status.equals("encs")) return encsUser.getEncsNamesList();
        else if (status.equals("comp")) return compUser.getCompNamesList();
        else if (status.equals("soen")) return soenUser.getSoenNamesList();
        else{
            System.out.println("Your request is invalid. Please choose from: admins,concordia,encs,comp,soen");
            return null;
        }
    }

    public static Map<String,String> getMap(){return map;}
    public static AdminUser getAdminUser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(adminUser == null){
           adminUser =(AdminUser) Class.forName("com.concordia.message_board.entities.AdminUser").newInstance();
        }
        return adminUser;
    }
    public static ConcordiaUser getConcordiaUser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(concordiaUser == null){
           concordiaUser =(ConcordiaUser) Class.forName("com.concordia.message_board.entities.ConcordiaUser").newInstance();
        }
        return concordiaUser;
    }

    public static EncsUser getEncsUser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(encsUser == null){
            encsUser =(EncsUser) Class.forName("com.concordia.message_board.entities.EncsUser").newInstance();
            encsUser.setEncsNamesList(getConcordiaUser().getConNamesList());
            encsUser.setConPostsList(getConcordiaUser().getConPostsList());
        }
        return encsUser;
    }
    public static CompUser getCompUser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(compUser == null){
            compUser =(CompUser) Class.forName("com.concordia.message_board.entities.CompUser").newInstance();
            compUser.setConNamesList(getConcordiaUser().getConNamesList());
            compUser.setEncsNamesList(getEncsUser().getEncsNamesList());
            //For Post
            compUser.setEncsPostsList(getEncsUser().getEncsPostsList());
            compUser.setConPostsList(getConcordiaUser().getConPostsList());

        }
        return compUser;
    }
    public static SoenUser getSoenUser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if(soenUser == null){
            soenUser =(SoenUser) Class.forName("com.concordia.message_board.entities.SoenUser").newInstance();
            soenUser.setEncsNamesList(getEncsUser().getEncsNamesList());
            soenUser.setConNamesList(getConcordiaUser().getConNamesList());
            //for Post
            soenUser.setEncsPostsList(getEncsUser().getEncsPostsList());
            soenUser.setConPostsList(getConcordiaUser().getConPostsList());
        }
        return soenUser;
    }


}
