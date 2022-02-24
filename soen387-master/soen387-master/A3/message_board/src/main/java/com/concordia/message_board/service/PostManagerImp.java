package com.concordia.message_board.service;

import com.concordia.message_board.entities.Post;
import com.concordia.message_board.mapper.PostMapper;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostManagerImp implements PostManager,UserManager{
    private ArrayList<Post> searchedPosts = new ArrayList<>();
    private ArrayList<Post> recentPosts = new ArrayList<>();

    @Autowired
    PostMapper postMapper;

    @Override
    public List<Post> getAllPost() {
      return postMapper.getAll();
    }

    @Override
    public void createPost(Post post) {
        //String userId,String postId,String postDate,String title,String content,Blob attachment,boolean edited
        postMapper.createPost(post.getUserId(),post.getPostId(),post.getPostDate(),
                post.getTitle(),post.getContent(), post.getAttachment(), post.isEdited());
    }

    @Override
    public List<Post> searchByUserId(String userId) {
        return null;
    }

    @Override
    public List<Post> searchByHashtag(String hashtag) {
        return null;
    }

    @Override
    public List<Post> searchByDate(String date) {
        return null;
    }

    @Override
    public void deleteByPostId(String postId) {
        postMapper.deletePostByPostId(postId);
    }

    @Override
    public String getPostTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);
        return date;
    }

    @Override
    public boolean authentication(String userId, String password) {
        try {
            File inputFile = new File("userGroup.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("user");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                //System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String temPassword= eElement
                            .getElementsByTagName("password")
                            .item(0)
                            .getTextContent();
                  //  System.out.println("Password--->"+temPassword);

                    String temUserId = eElement
                            .getElementsByTagName("userId")
                            .item(0)
                            .getTextContent();
                  //  System.out.println("Name----->"+temUserId);


                    if(temUserId.equals(userId)&&encoding(userId,password).equals(temPassword)) {
                        System.out.println("I found "+userId);
                        return true;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String encoding(String userId, String password) {

        String hashPassword =  new SimpleHash("SHA-256", password, userId+"soen387", 1024).toString();
        return hashPassword;
    }

    @Override
    public List<String> getList(String status) {
        return UserFactory.getSpecificList(status);
    }

    //support method
    public List<Post> getPostsForGroup(String group) {
        List<Post> ans = new ArrayList<>();
        try{
            if(group.equals("concordia")) ans = UserFactory.getConcordiaUser().getConPostsList();
            else if(group.equals("encs")) ans = UserFactory.getEncsUser().getEncsPostsList();
            else if(group.equals("comp")) ans = UserFactory.getCompUser().getCompPostsList();
            else if(group.equals("soen")) ans = UserFactory.getSoenUser().getSoenPostsList();
        }
        catch(Exception e){
            System.out.println("Sorry,Can't find your class with reflection");
        }
        try{UserFactory.getEncsUser().toString();}
        catch(Exception e){

        }

        return ans;
    }
    //support method

    public void clear(){
        try{
            UserFactory.getConcordiaUser().getConPostsList().clear();
            UserFactory.getEncsUser().getEncsPostsList().clear();
            UserFactory.getCompUser().getCompPostsList().clear();
            UserFactory.getSoenUser().getSoenPostsList().clear();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //support method
    public void sortPosts(List<Post> list) {
            clear();
        for(Post post:list){
            String membership = post.getMembership();
            try{
                if(membership.equals("concordia")) UserFactory.getConcordiaUser().addPostToList(post);
                else if(membership.equals("encs")) UserFactory.getEncsUser().addPostToList(post);
                else if(membership.equals("comp")) UserFactory.getCompUser().addPostToList(post);
                else if(membership.equals("soen")) UserFactory.getSoenUser().addPostToList(post);
                else if(membership.equals("public")||membership.equals("")){//remove null
                    UserFactory.getSoenUser().addPostToListForSoen(post);
                    UserFactory.getCompUser().addPostToList(post);
            }

            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Title-->"+post.getTitle()+post.getUserId());
                System.out.println("Sorry,can't find your class");
            }

        }
    }

    @Override
    public List<Post> getSortedListForGroup(List<Post> list, String group) {
        sortPosts(list);
        return getPostsForGroup(group);
    }

    @Override
    public void initializeFactory(String filePath) {
        try {
            File inputFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("user");
            System.out.println("Check "+filePath+" and initialize our UserFactory...");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
               // System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    String membership = eElement
                            .getElementsByTagName("membership")
                            .item(0)
                            .getTextContent();

                    String temUserId = eElement
                            .getElementsByTagName("userId")
                            .item(0)
                            .getTextContent();

                    if(membership.equals("admins")){
                        UserFactory.getAdminUser().add(temUserId);
                    }else if(membership.equals("concordia")){
                        UserFactory.getConcordiaUser().addUserIdToList(temUserId);
                    }else if(membership.equals("encs")){
                        UserFactory.getEncsUser().addUserIdToList(temUserId);
                    }else if(membership.equals("comp")){
                        UserFactory.getCompUser().addUserIdToList(temUserId);
                    }else if(membership.equals("soen")){
                        UserFactory.getSoenUser().addUserIdToList(temUserId);
                    }else{
                        System.out.println("Please check "+filePath+". Some users may have wrong group!");
                    }
                    if(temUserId != null)
                   UserFactory.getMap().put(temUserId,membership);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

