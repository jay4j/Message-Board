<<<<<<< HEAD
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatManagerImp implements ChatManager{

    private ArrayList<Message> messagesStore = new ArrayList<>();

    public ArrayList<Message> getMessagesStore(){
        return this.messagesStore;
    }
    @Override
    public void postMessage(String user, String message) {
        if(user == "")
            user = "anonymous";
        messagesStore.add(new Message(user,message));
    }

    @Override
    public List<Message> listMessages(String start, String end) {
        if(start == "" && end == "") return messagesStore;
        if(start == "") start = "1900/00/00 00:00:00";
        if(end == "") end = "2050/12/30 00:00:00";
        ArrayList<Message> filteredMessages = new ArrayList<>();
        //Here need to Lambda expression
        for(Message message : messagesStore){
            if(message.compareTo((Object)start) >= 0 && message.compareTo((Object)end) <= 0)
                filteredMessages.add(message);
        }
        return filteredMessages;
    }

    @Override
    public void clearChat(String start,String end) {
        if(start == "" && end == ""){
            messagesStore.clear();
            return;
        }
        if(start == "") start = "1900/01/01 00:00:00";
        if(end == "") end = "2080/01/01 00:00:00";
        ArrayList<Message> filteredMessages = new ArrayList<>();
        for(Message message : messagesStore){
            if(message.compareTo((Object)start) < 0 || message.compareTo((Object)end) > 0)
                filteredMessages.add(message);
        }
        messagesStore = filteredMessages;
    }
}
=======
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChatManagerImp implements ChatManager{

    private ArrayList<Message> messagesStore = new ArrayList<>();

    public ArrayList<Message> getMessagesStore(){
        return this.messagesStore;
    }
    @Override
    public void postMessage(String user, String message) {
        if(user == "")
            user = "anonymous";
        messagesStore.add(new Message(user,message));
    }

    @Override
    public List<Message> listMessages(String start, String end) {
        if(start == "" && end == "") return messagesStore;
        if(start == "") start = "1900/00/00 00:00:00";
        if(end == "") end = "2050/12/30 00:00:00";
        ArrayList<Message> filteredMessages = new ArrayList<>();
        //Here need to Lambda expression
        for(Message message : messagesStore){
            if(message.compareTo((Object)start) >= 0 && message.compareTo((Object)end) <= 0)
                filteredMessages.add(message);
        }
        return filteredMessages;
    }

    @Override
    public void clearChat(String start,String end) {
        if(start == "" && end == ""){
            messagesStore.clear();
            return;
        }
        if(start == "") start = "1900/01/01 00:00:00";
        if(end == "") end = "2080/01/01 00:00:00";
        ArrayList<Message> filteredMessages = new ArrayList<>();
        for(Message message : messagesStore){
            if(message.compareTo((Object)start) < 0 || message.compareTo((Object)end) > 0)
                filteredMessages.add(message);
        }
        messagesStore = filteredMessages;
    }
}
>>>>>>> c7c4cf8794e8e6d3a7514490f2bb8132db161006
