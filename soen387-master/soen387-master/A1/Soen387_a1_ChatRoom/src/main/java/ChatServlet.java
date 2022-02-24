<<<<<<< HEAD
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {

    private ChatManagerImp chatManager = new ChatManagerImp();
    ArrayList<Message> chatlist;
    public String chat_record = "";
    //List<Message> filteredMessages;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //get username
        String name = request.getParameter("username");
        //get message
        String message = request.getParameter("inputText");
        //store message
        chatManager.postMessage(name, message);
        chatlist = chatManager.getMessagesStore();

        chat_record += name + ": " + message + " " + "\n";

        request.setAttribute("inputText", chat_record);
        request.getRequestDispatcher("chat.jsp").forward(request,response);

        /*PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<body>");
        if(chatlist!=null)
        {
            for(Message msgs:chatlist)
            {
                out.println(msgs.toString());
                out.println("<br>");
            }
        }
        out.println("</body>");
        out.println("</HTML>");*/


        //response.getWriter().println(message);
        //response.sendRedirect("/chat.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
        /*String filename = "Chat Record.txt";
        String filePath = getServletContext().getRealPath(filename);

        File file = new File(filePath);

        response.setContentType("text/plain");
        //response.setContentType("text/xml");
        response.setHeader("Content-disposition","attachment; filename=" + filename);

        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        filteredMessages = chatManager.listMessages(null,null);

        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            outputStream.write(bytes);
        }

        fileInputStream.close();
        outputStream.close();*/

        /*int i = 0;
        while (!filteredMessages.isEmpty()) {
            outputStream.write(filteredMessages.indexOf(i));
        }

        outputStream.flush();
        outputStream.close();*/


        /*String format = request.getParameter("format");
        if (format.equals("XML")) {
            response.setContentType("text/xml");
        }*/
    }
}
=======
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChatServlet")
public class ChatServlet extends HttpServlet {

    private ChatManagerImp chatManager = new ChatManagerImp();
    ArrayList<Message> chatlist;
    public String chat_record = "";
    //List<Message> filteredMessages;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //get username
        String name = request.getParameter("username");
        //get message
        String message = request.getParameter("inputText");
        //store message
        chatManager.postMessage(name, message);
        chatlist = chatManager.getMessagesStore();

        chat_record += name + ": " + message + " " + "\n";

        request.setAttribute("inputText", chat_record);
        request.getRequestDispatcher("chat.jsp").forward(request,response);

        /*PrintWriter out = response.getWriter();
        out.println("<HTML>");
        out.println("<body>");
        if(chatlist!=null)
        {
            for(Message msgs:chatlist)
            {
                out.println(msgs.toString());
                out.println("<br>");
            }
        }
        out.println("</body>");
        out.println("</HTML>");*/


        //response.getWriter().println(message);
        //response.sendRedirect("/chat.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request,response);
        /*String filename = "Chat Record.txt";
        String filePath = getServletContext().getRealPath(filename);

        File file = new File(filePath);

        response.setContentType("text/plain");
        //response.setContentType("text/xml");
        response.setHeader("Content-disposition","attachment; filename=" + filename);

        FileInputStream fileInputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        filteredMessages = chatManager.listMessages(null,null);

        int bytes;
        while ((bytes = fileInputStream.read()) != -1) {
            outputStream.write(bytes);
        }

        fileInputStream.close();
        outputStream.close();*/

        /*int i = 0;
        while (!filteredMessages.isEmpty()) {
            outputStream.write(filteredMessages.indexOf(i));
        }

        outputStream.flush();
        outputStream.close();*/


        /*String format = request.getParameter("format");
        if (format.equals("XML")) {
            response.setContentType("text/xml");
        }*/
    }
}
>>>>>>> c7c4cf8794e8e6d3a7514490f2bb8132db161006
