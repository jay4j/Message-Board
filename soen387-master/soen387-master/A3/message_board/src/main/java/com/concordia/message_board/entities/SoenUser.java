package com.concordia.message_board.entities;

import java.util.ArrayList;
import java.util.List;

public class SoenUser {
    private List<String> encsNamesList;
    private List<String> conNamesList;
    private List<String> soenNamesList;

    private List<Post> encsPostsList;
    private List<Post> conPostsList;
    private List<Post> soenPostsList;

    public SoenUser() {
        soenPostsList = new ArrayList<>();
        conPostsList = new ArrayList<>();
        encsPostsList = new ArrayList<>();
        encsNamesList = new ArrayList<>();
        soenNamesList = new ArrayList<>();
        conNamesList = new ArrayList<>();
    }

//----------------------------setter and getter for soen-------------------------


    public List<Post> getEncsPostsList() {
        return encsPostsList;
    }

    public void setEncsPostsList(List<Post> encsPostsList) {
        this.encsPostsList = encsPostsList;
    }

    public List<Post> getConPostsList() {
        return conPostsList;
    }

    public void setConPostsList(List<Post> conPostsList) {
        this.conPostsList = conPostsList;
    }

    public List<Post> getSoenPostsList() {
        return soenPostsList;
    }

    public void setSoenPostsList(List<Post> soenPostsList) {
        this.soenPostsList = soenPostsList;
    }

    //----------------------------add post to list---------------------------------------------
    public void addPostToList(Post post){
        encsPostsList.add(post);
        soenPostsList.add(post);
        conPostsList.add(post);
    }

    //-----------------------------------------------------------


    public List<String> getEncsNamesList() {
        return encsNamesList;
    }

    public void setEncsNamesList(List<String> encsNamesList) {
        this.encsNamesList = encsNamesList;
    }

    public List<String> getConNamesList() {
        return conNamesList;
    }

    public void setConNamesList(List<String> conNamesList) {
        this.conNamesList = conNamesList;
    }

    public List<String> getSoenNamesList() {
        return soenNamesList;
    }

    public void addUserIdToList(String userId){
        encsNamesList.add(userId);
        conNamesList.add(userId);
        soenNamesList.add(userId);
    }
    public void addPostToListForSoen(Post post){
        soenPostsList.add(post);
    }
    @Override
    public String toString() {
        String li = "The following are Soen users:\n";
        for(String str:soenNamesList){
            li += "\n"+str;
        }
        li += "\nThe following are Soen posts:\n";
        for(Post post:soenPostsList){
            li +="\n"+post;
        }
        return li;
    }
}
