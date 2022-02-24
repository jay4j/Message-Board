package com.concordia.chatroom.entity;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component // java bean, put this class into spring container and it is unique
public class Message implements Comparable{ // to compare two date(from year to second)

    // message info
    private String content;
    // the message sender
    private String user;
    // the message time
    private String date;

    public Message() {
    }

    //constructor to form a message using current time
    public Message(String user, String content) {
        this.content = content;
        this.user = user;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);
        this.date = date;
    }

    //constructor using the specific time
    public Message(String content, String user, String date) {
        this.content = content;
        this.user = user;
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + content + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                "}";
    }

    // compare the two date
    @Override
    public int compareTo(Object o) {
        String str1 = this.date;
        String str2 = (String) o;
        int[] origin = strToInt(str1);
        int[] opponent = strToInt(str2);
        for(int i = 0; i < origin.length; i++){
            if(origin[i] > opponent[i]) return 1;
            else if(origin[i] < opponent[i]) return -1;
        }
        return 0;
    }
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
}
