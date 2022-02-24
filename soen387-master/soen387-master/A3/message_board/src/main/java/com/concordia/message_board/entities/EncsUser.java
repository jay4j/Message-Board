package com.concordia.message_board.entities;

import java.util.ArrayList;
import java.util.List;

public class EncsUser {
    private List<String> encsNamesList;
    protected List<String> conNamesList;
    private List<Post> encsPostsList;
    protected List<Post> conPostsList;

    public EncsUser(){
        encsNamesList = new ArrayList<>();
        conNamesList = new ArrayList<>();
        encsPostsList = new ArrayList<>();
        conPostsList = new ArrayList<>();

    }

    public List<String> getEncsNamesList() {
        return encsNamesList;
    }

    public void setEncsNamesList(List<String> conNamesList) {
        this.conNamesList = conNamesList;
    }

    public List<Post> getEncsPostsList() {
        return encsPostsList;
    }

    public void setEncsPostsList(List<Post> encsPostsList) {
        this.encsPostsList = encsPostsList;
    }

    public List<Post> getConPostsList() {
        return conPostsList;
    }
    //Use a concoridaUser to set it's ConPostsList and can initialize a conUser
    public void setConPostsList(List<Post> conPostsList) {
        this.conPostsList = conPostsList;
    }

    public void addUserIdToList(String userId){
        this.encsNamesList.add(userId);
        this.conNamesList.add(userId);
        this.toString();
    }
    public void addPostToList(Post post){
        this.encsPostsList.add(post);
        this.conPostsList.add(post);
    }

    @Override
    public String toString() {
        String li = "The following are Encs users:\n";
        for(String str:encsNamesList){
            li += "\n"+str;
        }
        li += "\nThe following are Concordia posts:\n";
        for(Post post:encsPostsList){
            li +="\n"+post;
        }
        return li;
    }
}
