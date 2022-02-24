package com.concordia.message_board.entities;

import java.util.ArrayList;
import java.util.List;

public class CompUser {
    private List<String> encsNamesList;
    private List<String> conNamesList;
    private List<String> compNamesList;

    private List<Post> encsPostsList;
    private List<Post> conPostsList;
    private List<Post> compPostsList;

    public CompUser() {
        compNamesList = new ArrayList<>();
        conNamesList = new ArrayList<>();
        encsNamesList = new ArrayList<>();
        compPostsList = new ArrayList<>();
        conPostsList = new ArrayList<>();
        encsPostsList = new ArrayList<>();

    }
    //-------------------setter and getter for post---------------


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

    public List<Post> getCompPostsList() {
        return compPostsList;
    }

    public void setCompPostsList(List<Post> compPostsList) {
        this.compPostsList = compPostsList;
    }

    //----------------------------------------------------
    //--------------------add post---------------------

    public void addPostToList(Post post){
       // System.out.println(compPostsList.size()+" :<<<<*************************comp List size");
        encsPostsList.add(post);
        compPostsList.add(post);
        conPostsList.add(post);
    }
    //--------------------------------------------------
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

    public List<String> getCompNamesList() {
        return compNamesList;
    }

    public void setCompNamesList(List<String> compNamesList) {
        this.compNamesList = compNamesList;
    }
    public void addUserIdToList(String userId){
        encsNamesList.add(userId);
        conNamesList.add(userId);
        compNamesList.add(userId);
    }
    @Override
    public String toString() {
        String li = "The following are Comp users:\n";
        for(String str:encsNamesList){
            li += "\n"+str;
        }
        li += "\nThe following are Comp posts:\n";
        for(Post post:encsPostsList){
            li +="\n"+post;
        }
        return li;
    }
}
