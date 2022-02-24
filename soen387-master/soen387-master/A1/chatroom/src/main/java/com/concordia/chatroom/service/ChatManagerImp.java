package com.concordia.chatroom.service;

import com.concordia.chatroom.entity.Message;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class ChatManagerImp implements ChatManager {
    private ArrayList<Message> messagesStore = new ArrayList<>();

    // get the all messages from the chatroom
    @Override
    public ArrayList<Message> getMessagesStore(){
        return this.messagesStore;
    }
    // user submit a specific message
    @Override
    public void postMessage(String user, String message) {
        messagesStore.add(new Message(user,message));
    }

    // filter the messages between the specified time
    @Override
    public List<Message> listMessages(String start, String end) {
        ArrayList<Message> filteredMessages = new ArrayList<>();
           for(Message message : messagesStore){
               if(message.compareTo((Object)start) >= 0 && message.compareTo((Object)end) <= 0)
                   filteredMessages.add(message);
           }
        return filteredMessages;
    }

   // filter the specified dated messages that we don't need again
    @Override
    public void clearChat(String start,String end) {

        ArrayList<Message> filteredMessages = new ArrayList<>();
        for(Message message : messagesStore){
            if(message.compareTo((Object)start) < 0 || message.compareTo((Object)end) > 0)
                filteredMessages.add(message);
        }
        System.out.println("*************************clear**********************");
        messagesStore = filteredMessages;
    }

    //check the date is valid or not in terms of format and logical input
    @Override
    public boolean validateFormat(String dateStr){
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try{
            //format check
            Date date = format.parse(dateStr);
        }catch(Exception e){
            System.out.println("false");
            return false;
        }

        int[] ans = new int[6];

        //int year,month,day,hour,minute,second ;
        ans[0] = Integer.valueOf(dateStr.substring(0, 4));
        ans[1] = Integer.valueOf(dateStr.substring(5, 7));
        ans[2] = Integer.valueOf(dateStr.substring(8, 10));
        ans[3] = Integer.valueOf(dateStr.substring(11, 13));
        ans[4] = Integer.valueOf(dateStr.substring(14, 16));
        ans[5] = Integer.valueOf(dateStr.substring(17, 19));

        // logic check
        if(ans[0] < 0 || ans[1] < 1 || ans[1] > 12 || ans[2] < 0 || ans[2] > 31 ||
                ans[3] < 0 || ans[3] > 23 || ans[4] < 0 || ans[4] > 59 ||ans[5] < 0 || ans[5] >59){

            return false;
        }
        return true;
    }

    //Listener filter to check the Referer
    @Override
    public boolean refererBlocked(HttpServletRequest request) {
        return request.getHeader("Referer") == null;
    }


}
