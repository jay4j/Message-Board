package com.concordia.chatroom.service;

import com.concordia.chatroom.entity.Message;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

// business layer to be implemented
public interface ChatManager {

   void postMessage(String user, String message);

   List<Message> listMessages(String start, String end);

   void clearChat(String start,String end);

   boolean validateFormat(String date);

   boolean refererBlocked(HttpServletRequest request);

   ArrayList<Message> getMessagesStore();

}
