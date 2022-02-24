package com.concordia.message_board.entities;

import java.util.ArrayList;
import java.util.List;

public class ConcordiaUser {
    private List<String> conNamesList;
    //extra info from database and list all posts for concordia
    private List<Post> conPostsList;

    public ConcordiaUser() {
        conNamesList = new ArrayList<>();
        conPostsList = new ArrayList<>();
    }

    public List<Post> getConPostsList() {
        return conPostsList;
    }

    public void setConPostsList(List<Post> conPostsList) {
        this.conPostsList = conPostsList;
    }

    public List<String> getConNamesList() {
        return conNamesList;
    }

    public void setConNamesList(List<String> conNamesList) {
        this.conNamesList = conNamesList;
    }

    public void addUserIdToList(String userId){
        conNamesList.add(userId);
    }
    public void addPostToList(Post post){
        conPostsList.add(post);
        System.out.println(conPostsList.size()+" :<<<<*************************con list size");
    }
    @Override
    public String toString() {
        String li = "The following are Concordia users:\n";
        for(String str:conNamesList){
            li += "\n"+str;
        }
        li += "\nThe following are Concordia posts:\n";
        for(Post post:conPostsList){
            li +="\n"+post;
        }
        return li;
    }
}
