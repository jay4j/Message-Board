package com.concordia.message_board.entities;

import java.io.InputStream;
import java.sql.Blob;
import java.util.Arrays;
//---------------------------------add interface--------------------
public class Post implements Comparable{
    private String userId;
    private String postId;
    private String title;
    private String content;
    private String postDate;
    //private Blob attachBlob;
    private boolean edited;
    private Attachment attachment;

    public Post() {
    }

    public Post(String userId,String postId, String title, String content, String postDate, Attachment attachment){
        this.userId = userId;
        this.title = title;
        this.postId = postId;
        this.content = content;
        this.postDate = postDate;
        //this.attachBlob = attachBlob;
        this.attachment = attachment;
        this.edited = false;
    }

    public Post(Post copyPost){
        this.userId = copyPost.userId;
        this.postId = copyPost.postId;
        this.title = copyPost.title;
        this.content = copyPost.content;
        this.postDate = copyPost.postDate;
        //this.attachBlob = copyPost.attachBlob;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    /*public Blob getAttachBlob(){
        return attachBlob;
    }*/

    /*public void setAttachBlob(Blob attachBlob){
        this.attachBlob = attachBlob;
    }*/


    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Post{" +
                "userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", content='" + content + '\'' +
                ", date='" + postDate + '\'' +
                ", attachment=" + attachment.getFileName() +'\'' +
                '}';
    }
//--------------------helper to sort Posts---------------------------

    //convert string date to int type which is comparable
    public int[] strToInt(String date){
        int[] ans = new int[6];
        try {
            ans[0] = Integer.valueOf(date.substring(0, 4));
            ans[1] = Integer.valueOf(date.substring(5, 7));
            ans[2] = Integer.valueOf(date.substring(8, 10));
            ans[3] = Integer.valueOf(date.substring(11, 13));
            ans[4] = Integer.valueOf(date.substring(14, 16));
            ans[5] = Integer.valueOf(date.substring(17, 19));
        }catch(NumberFormatException e){
            System.out.println("The input format is invalid...");
        }
        return ans;
    }
// add compareTo to sort the posts in ArrayList by date
    //--------------------------implement compareTo method--------------------------------------
    @Override
    public int compareTo(Object o) {
        String thisDate = this.getPostDate();
        String targetDate = ((Post) o).getPostDate();
        int[] thisArr = this.strToInt(thisDate);
        int [] targetArr = ((Post) o).strToInt(targetDate);
        for(int i = 0; i < thisArr.length; i++){
            if(thisArr[i] != targetArr[i]) return thisArr[i] > targetArr[i] ? -1 : 1;
        }
        return 0;
    }

    public int compareToString(Object o) {
        String str1 = this.postDate;
        String str2 = (String) o;
        int[] origin = strToInt(str1);
        int[] opponent = strToInt(str2);
        for(int i = 0; i < origin.length; i++){
            if(origin[i] > opponent[i]) return 1;
            else if(origin[i] < opponent[i]) return -1;
        }
        return 0;
    }

    }


