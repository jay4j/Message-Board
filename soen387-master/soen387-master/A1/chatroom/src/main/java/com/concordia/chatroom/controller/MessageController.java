package com.concordia.chatroom.controller;

import com.concordia.chatroom.entity.Message;
import com.concordia.chatroom.service.ChatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

@Controller
public class MessageController {
    @Autowired
    ChatManager chatManager;

    // handle the /message request
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String chatRoom(@RequestParam(value = "username", required = false) String username,
                           @RequestParam(value = "message", required = true) String message,
                           Map<String, Object> map,HttpServletRequest request) {
        //Referer check
        //if(chatManager.refererBlocked(request)) return "reject";

         //if the username doesn't exit assign it to anonymous
        if (username == "" || username == null) username = "anonymous";

        // submit the user's message
        chatManager.postMessage(username, message);

        //display all post messages
        map.put("messages", chatManager.getMessagesStore());

        // give a name to chatroom
        map.put("chatRoomName","Concordia");
        return "chatRoom";
    }

    // index handle "/"
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(HttpServletRequest request){
        return "chatRoom";
    }

    // Handle download request
    @RequestMapping(value="/download", method=RequestMethod.GET)
    public String download(@RequestParam(value = "format", required = false) String format,
                           @RequestParam(value = "from", required = false) String from,
                           @RequestParam(value = "to", required = false) String to,
                           HttpServletResponse  response,HttpServletRequest request,
                           Map<String, Object> map) throws Exception {
        // Referer check
        //if(chatManager.refererBlocked(request)) return "reject";
        // offer a title to this page
        map.put("chatRoomName","Chat Download");

        // if start time ignored, we assign a remote time to it
        if (from == "" || from == null){
            from = "1900/01/01 00:00:00";
        }
        // if end time ignored, we assign a furture time to it
        if (to == "" ||  to == null){
            to = "2050/12/30 00:00:00";
        }

        //if the date input is valid
        if ( chatManager.validateFormat(from) && chatManager.validateFormat(to)) {
            //put wanted messages to a list
            List<Message> chatRecord;
            chatRecord = chatManager.listMessages(from, to);
            // determine the file type
            if (format.equals("txt")) {
                //give a file name
                String filename = "Chat Record.txt";
                // set contentType in response
                response.setContentType("text/plain");
                // set the download type
                response.setHeader("Content-Disposition", "attachment;filename= " + filename);
                // get a output stream from response
                PrintWriter out = response.getWriter();
                Message currentMsg;

                for (int i = 0; i < chatRecord.size(); i++) {
                    currentMsg = chatRecord.get(i);
                    //write data into stream with Ascii code
                    out.println(currentMsg.toString());
                }
                out.flush();
                out.close();
            }
             else {
                String filename = "Chat Record.xml";
                response.setContentType("text/xml");
                response.setHeader("Content-Disposition", "attachment;filename= " + filename);

                PrintWriter out = response.getWriter();
                Message currentMsg;
                //write the data out using xml type
                out.println("<Chat>");
                for (int i = 0; i < chatRecord.size(); i++) {
                    currentMsg = chatRecord.get(i);
                    out.println("<message>");
                    out.println("\t<name>" + currentMsg.getUser() + "</name>");
                    out.println("\t<date>" + currentMsg.getDate() + "</date>");
                    out.println("\t<content>" + currentMsg.getContent() + "</content>");
                    out.println("</message>\n");
                }
                out.println("</Chat>");
                out.flush();
                out.close();
            }

            return "chatRoom";
        }
        else{
            //if date input is error, we show the error info
            map.put("errorMsg1", "*Error: Invalid Data format");
            map.put("messages", chatManager.getMessagesStore());
            return "chatRoom";
        }
    }

    //Handle /clear request
    @RequestMapping(value = "/clear",method = RequestMethod.GET)
    public String clear(@RequestParam(value = "from", required = false) String from,
                        @RequestParam(value = "to", required = false) String to,
                        Map<String, Object> map,HttpServletRequest request){
        //check Referer
        //if(chatManager.refererBlocked(request)) return "reject";
        //give it a proper title
        map.put("chatRoomName","Chat Cleaned");
        // if start time is ignored, assign a remote time
        if (from == "" || from == null){ //null for curl
            from = "1900/01/01 00:00:00";
        }
        // if end time is ignored, assign a future time
        if (to == "" || to == null){ //null for curl
            to = "2050/12/30 00:00:00";
        }

        //if input is valid
        if (chatManager.validateFormat(from) && chatManager.validateFormat(to)) {
            // use the service layer to clear the messages
            chatManager.clearChat(from, to);
            // put the filtered messages to messages variable
            map.put("messages", chatManager.getMessagesStore());
            return "chatRoom";
        }
        else{
            // if valid input, display error info
            map.put("errorMsg2", "*Error: Invalid Data format");
            // keep all the messages
            map.put("messages", chatManager.getMessagesStore());
            return "chatRoom";
        }
    }

    //handle refresh request to keep all messages up-dated
    @RequestMapping(value = "/refresh",method = RequestMethod.GET)
    public String refresh(Map<String, Object> map,HttpServletRequest request){
        //check Referer
        if(chatManager.refererBlocked(request)) return "reject";
        map.put("chatRoomName","Concordia");
        //flush all messages in different pages
        map.put("messages", chatManager.getMessagesStore());

        return "chatRoom";
    }
}
