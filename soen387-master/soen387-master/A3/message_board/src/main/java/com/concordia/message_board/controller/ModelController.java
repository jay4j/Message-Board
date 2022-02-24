package com.concordia.message_board.controller;

import com.concordia.message_board.entities.Attachment;
import com.concordia.message_board.entities.Post;
import com.concordia.message_board.entities.XMLFile;
import com.concordia.message_board.mapper.MessageMapper;
import com.concordia.message_board.service.PostManager;
import com.concordia.message_board.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialBlob;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Element;
import java.io.*;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
public class ModelController {

    @Autowired
    PostManager postManager;
    MessageMapper messageMapper;
    @Autowired
    UserManager userManager;


    @Value("${display.number}")
    private String number;

    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String post(@RequestParam(value = "title",required = false) String title,
                       @RequestParam(value = "content") String content,
                       @RequestParam(value = "file",required = false) MultipartFile file,
                       @RequestParam(value = "membership",required = false) String membership,
                       Model model, HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
        String date = messageMapper.getPostTime();
        String postID = UUID.randomUUID().toString().substring(0,12);
        String userId = (String)session.getAttribute("userId");

        Blob blob;
        String fileName = file.getOriginalFilename();
        String fileType = file.getContentType();
        Long fileSize = file.getSize();
        Attachment attachment = null;

        if (!file.isEmpty()) {
            //in = file.getInputStream();
            byte[] bytes = file.getBytes();
            blob = new SerialBlob(bytes);

            String attachId = UUID.randomUUID().toString().substring(0,12);
            attachment = new Attachment(attachId,postID,fileName,fileType,fileSize,blob);
        }

        Post post = new Post(userId,postID,title,content,date, attachment,membership);
        messageMapper.insertIntoDB(post);

        List<Post> posts;
        if(session.getAttribute("membership").equals("admins")){
            posts = messageMapper.getAllPost();
        }else{
            posts = messageMapper.getUserPost(userId);
        }
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));

        return "postMessage";
    }

    @RequestMapping(value = "/updatePost", method = RequestMethod.POST)
    public String repost(@RequestParam(value = "postId",required = false) String postId,
                         @RequestParam(value = "title",required = false) String title,
                         @RequestParam("content") String content,
                         @RequestParam(value = "file",required = false) MultipartFile file,
                         @RequestParam(value = "membership",required = false) String membership,
                         Model model, HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
        String date = messageMapper.getPostTime();
        String userId = (String)session.getAttribute("userId");
        //get post from Database
        Post oldPost = messageMapper.extractSpecificPost(postId);
        oldPost.setTitle(title);
        oldPost.setPostDate(date);
        oldPost.setContent(content);
        //add membership
        oldPost.setMembership(membership);

        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileType = file.getContentType();
            Long fileSize = file.getSize();
            byte[] bytes = file.getBytes();
            Blob blob = new SerialBlob(bytes);

            String attachId = UUID.randomUUID().toString().substring(0,12);
            Attachment attachment = new Attachment(attachId, oldPost.getPostId(), fileName, fileType, fileSize, blob);

            oldPost.setAttachment(attachment);
        }
        // show edited if post is updated
        //----------------------------------------------------------------
        oldPost.setEdited(true);
        messageMapper.updatePost(oldPost);
        List posts = messageMapper.getUserPost(userId);
        //--------------------sort posts---------------
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));
        return "postMessage";
    }

    @PostMapping("/delete")
    public String deleteByPostId(String postId){
        postManager.deleteByPostId(postId);
        return "redirect:/afterDelete";
    }

    @GetMapping("/allPosts")
    public String allPosts(Model model,HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
        List<Post> posts = messageMapper.getAllPost();
        //-----------------------------sort posts by time-------------------------
        String membership = (String)session.getAttribute("membership");
        if(!membership.equals("admins")){
            posts = userManager.getSortedListForGroup(posts,membership);
        }
        Collections.sort(posts);

        List<Post> tenPosts = new ArrayList<>();
        int length = Integer.valueOf(number);

        if (posts.size() < length){
            length = posts.size();
        }
        for (int i = 0; i < length; i++){
            tenPosts.add(posts.get(i));
        }

        model.addAttribute("posts", tenPosts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));

        return "viewMessage";
    }

    @GetMapping("/afterDelete")
    public String afterDelete(Model model,
                              HttpSession session) throws Exception {
        messageMapper = new MessageMapper();
        String userId = (String)session.getAttribute("userId");
        List<Post> posts = messageMapper.getUserPost(userId);
        //-----------------------------sort posts by time-------------------------
        String membership = (String)session.getAttribute("membership");
        if(membership.equals("admins")){
            posts = messageMapper.getAllPost();
        }
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));
        return "postMessage";
    }

    @GetMapping("/back")
    public String back(Model model,
                       HttpSession session) throws Exception {
        messageMapper = new MessageMapper();
        String userId = (String)session.getAttribute("userId");
        List<Post> posts = messageMapper.getUserPost(userId);
        String membership = (String)session.getAttribute("membership");
        if(membership.equals("admins")){
            posts = messageMapper.getAllPost();
        }
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));
        return "postMessage";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "tag") String tag,
                         @RequestParam(value = "userId") String userId,
                         @RequestParam(value = "from") String from,
                         @RequestParam(value = "to") String to,
                         Model model) throws Exception {

        messageMapper = new MessageMapper();
        List<Post> posts = messageMapper.getAllPost();
        if (!tag.equals("")) {
            posts = messageMapper.getPostsByHashTag(tag, posts);
        }
        if (!userId.equals("")) {
            posts = messageMapper.getPostsByUserId(userId, posts);
        }
        if (from == "" || from == null){
            from = "1900/01/01 00:00:00";
        }
        if (to == "" ||  to == null){
            to = "2050/12/30 00:00:00";
        }
        if ( messageMapper.validateFormat(from) && messageMapper.validateFormat(to)) {
            posts = messageMapper.getPostsByDate(from,to,posts);
        }
        else {
            model.addAttribute("errorMsg", "*Error: Invalid Data format");
        }
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        return "viewMessage";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@RequestParam(value = "postId",required = false) String postId,
                       @RequestParam(value = "file",required = false) Attachment file,
                       Model model) throws Exception {

        messageMapper = new MessageMapper();
        Post oldPost = messageMapper.extractSpecificPost(postId);
        model.addAttribute("post",oldPost);
        return "editMessage";
    }

    @RequestMapping(value = "/file/{postId}", method = RequestMethod.GET)
    public void downloadeAttach(@PathVariable ("postId") String postId,
                              Model model,
                              HttpServletResponse response) throws Exception {

        messageMapper = new MessageMapper();
        Post post = messageMapper.extractSpecificPost(postId);
        InputStream in = new ByteArrayInputStream(messageMapper.getAttachData(postId));
        //give a file name
        String filename = post.getAttachment().getFileName();
        // set contentType in response
        response.setContentType(post.getAttachment().getFileType());
        // set the download type
        response.setHeader("Content-Disposition", "attachment;filename= " + filename);

        OutputStream out = response.getOutputStream();
        byte[] buffer= new byte[4096];
        int bytesRead;

        while ((bytesRead = in.read(buffer)) != -1){
            out.write(buffer,0, bytesRead);
        }

        in.close();
        out.close();

    }

    @RequestMapping(value = "/deleteAttach/{postId}", method = RequestMethod.GET)
    public String deleteAttach(@PathVariable ("postId") String postId,
                               Model model,
                               HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
        String userId = (String)session.getAttribute("userId");
        Post post = messageMapper.extractSpecificPost(postId);

        Boolean delete = messageMapper.deleteAttach(post.getPostId());
        Attachment attachment = new Attachment();
        post.setAttachment(attachment);
        String postTime = messageMapper.getPostTime();
        post.setPostDate(postTime);
        post.setEdited(true);

        messageMapper.updatePost(post);
        List<Post> posts = messageMapper.getUserPost(userId);
        Collections.sort(posts);
        model.addAttribute("posts", posts);
        model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));
        return "postMessage";
    }

    //A3 download
    @RequestMapping(value = "/downloadXML", method = RequestMethod.GET)
    public void downloadXml(HttpServletResponse  response,HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
       List<Post> posts = messageMapper.getAllPost();
       Collections.sort(posts);
        if(!session.getAttribute("membership").equals("admins"))
        posts = userManager.getSortedListForGroup(posts,(String)session.getAttribute("membership"));
        List<Post> tenPosts = new ArrayList<>();
        int length = Integer.valueOf(number);

        if (posts.size() < length){
            length = posts.size();
        }
        for (int i = 0; i < length; i++){
            tenPosts.add(posts.get(i));
        }

        String filename = "postList.xml";
        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment;filename= " + filename);

        PrintWriter out = response.getWriter();
        Post currentPost;
        //write the data out using xml type
        out.println("<PostList>");
        for (int i = 0; i < tenPosts.size(); i++) {
            currentPost = tenPosts.get(i);
            out.println("<Post>");
            out.println("\t<name>" + currentPost.getUserId() + "</name>");
            out.println("\t<date>" + currentPost.getPostDate() + "</date>");
            out.println("\t<title>" + currentPost.getTitle() + "</title>");
            out.println("\t<content>" + currentPost.getContent() + "</content>");
            out.println("\t<attachment>" + currentPost.getAttachment().getFileName() + "</attachment>");
            out.println("</Post>\n");
        }
        out.println("</PostList>");
        out.flush();
        out.close();
    }

    @RequestMapping(value = "/transformView", method = RequestMethod.GET)
    public void xmlToHtml(HttpServletResponse  response,HttpSession session) throws Exception {

        messageMapper = new MessageMapper();
        List<Post> posts = messageMapper.getAllPost();
        Collections.sort(posts);
        if(!session.getAttribute("membership").equals("admins"))
        posts = userManager.getSortedListForGroup(posts,(String)session.getAttribute("membership"));
        List<Post> tenPosts = new ArrayList<>();
        int length = Integer.valueOf(number);

        if (posts.size() < length){
            length = posts.size();
        }
        for (int i = 0; i < length; i++){
            tenPosts.add(posts.get(i));
        }

        XMLFile xmlFile = new XMLFile();

        //XML to HTML
        response.setContentType("text/html;charset=UTF 8");
        //File xml = new File ("postList.xml");
        File xsl = new File ("postList.xsl");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element rootElement = doc.createElement("PostList");
        doc.appendChild(rootElement);

        for (Post post:tenPosts) {
            String name = post.getUserId();
            String date = post.getPostDate();
            String title = post.getTitle();
            String content = post.getContent();
            String attachName = post.getAttachment().getFileName();
            rootElement.appendChild(xmlFile.createPostElement(doc, name, date, title, content, attachName));
        }
        //Document document = builder.parse(xml);
        DOMSource source = new DOMSource(doc);

        PrintWriter out = response.getWriter();
        TransformerFactory tFactory = TransformerFactory.newInstance();
        StreamSource xslt = new StreamSource(xsl);
        Transformer transformer = tFactory.newTransformer(xslt);

        StreamResult result = new StreamResult(out);
        transformer.transform(source, result);

    }
}
