package com.concordia.message_board.mapper;

import com.concordia.message_board.entities.Attachment;
import com.concordia.message_board.entities.Post;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageMapper {

    private String jdbcName ="com.mysql.cj.jdbc.Driver";
    private Connection conn;
    private String serverName = "root";
    private String password = "comp353";

    public MessageMapper(){
    }

    public Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/concordia?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT", serverName, password);
        return conn;
    }

    public void clearTable() throws Exception {
        conn = getCon();
        Statement stm = conn.createStatement();
        stm.execute("DROP TABLE IF EXISTS post;");
        System.out.println("Table deleted");
        conn.close();
    }

    public void insertIntoDB(Post post) throws Exception {
        conn = getCon();

        String postId = post.getPostId();
        String userId = post.getUserId();
        String title = post.getTitle();
        String postDate = post.getPostDate();
        String content = post.getContent();
        boolean updated = post.isEdited();
        Attachment attachment = post.getAttachment();

        //String query = "INSERT INTO post(postid, userid, title, postdate, content, attachment) value(?,?,?,?,?,?)";
        String query = "INSERT INTO post(postid, userid, title, postdate, content, updated) value(?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1,postId);
        statement.setString(2,userId);
        statement.setString(3,title);
        statement.setString(4,postDate);
        statement.setString(5,content);
        statement.setBoolean(6,updated);

        statement.execute();
        statement.close();
        conn.close();
        //insert attachment
        if (attachment != null){
            insertAttach(attachment);
        }
        // debug
        System.out.println("Insert Post to Database");
    }

    public boolean updatePost(Post post) throws Exception {

        conn = getCon();

        String query = "UPDATE post SET title=?, postDate=?, content=?, updated = ? WHERE postId=?";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, post.getTitle());
        ps.setString(2, post.getPostDate());
        ps.setString(3, post.getContent());
        ps.setBoolean(4, post.isEdited());
        ps.setString(5, post.getPostId());


        int i = ps.executeUpdate();

        if (post.getAttachment().getAttachId() != null){
            insertAttach(post.getAttachment());
        }

        conn.close();

        if(i == 1) {
            return true;
        }

        return false;
    }

    public List<Post> getAllPost(String number) throws Exception {
        conn = getCon();
        List<Post> allPost = new ArrayList<>();
        Statement stm = conn.createStatement();
        ResultSet result = stm.executeQuery("select * from post");

        int count = 0;
        while (result.next() && count < Integer.valueOf(number)){
            Post post = extractPostFromResultSet(result);
            allPost.add(post);
            count++;
        }
        conn.close();
        return allPost;
    }

    public List<Post> getAllPost() throws Exception {
        conn = getCon();
        List<Post> allPost = new ArrayList<>();
        Statement stm = conn.createStatement();
        ResultSet result = stm.executeQuery("select * from post");

        while (result.next()){
            Post post = extractPostFromResultSet(result);
            allPost.add(post);
        }
        conn.close();
        return allPost;
    }

    // get User's Posts
    public List<Post> getUserPost(String id) throws Exception {
        conn = getCon();
        List<Post> userPost = new ArrayList<>();
        String query = "select * from post where userId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,id);
        ResultSet result = statement.executeQuery();

        while (result.next()){
            Post post = extractPostFromResultSet(result);
            userPost.add(post);
        }
        conn.close();
        return userPost;
    }

    public Post extractPostFromResultSet(ResultSet rs) throws Exception {
        conn = getCon();
        Post post = new Post();
        post.setPostId( rs.getString("postId") );
        post.setUserId( rs.getString("userId") );
        post.setTitle( rs.getString("title") );
        post.setPostDate( rs.getString("postDate") );
        post.setContent( rs.getString("content") );
        post.setEdited(rs.getBoolean("updated"));

        Attachment attachment = extractAttachFromResultSet(post.getPostId());
        post.setAttachment(attachment);

        conn.close();
        return post;
    }

    public Post extractSpecificPost(String postId) throws Exception {

        conn = getCon();
        String query = "select * from post where postId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,postId);
        ResultSet rs = statement.executeQuery();

        Post post = new Post();
        if(rs.next()) {
            post.setPostId(rs.getString("postId"));
            post.setUserId(rs.getString("userId"));
            post.setTitle(rs.getString("title"));
            post.setPostDate(rs.getString("postDate"));
            post.setContent(rs.getString("content"));
            post.setEdited(rs.getBoolean("updated"));
        }
        Attachment attachment = extractAttachFromResultSet(post.getPostId());
        post.setAttachment(attachment);
        conn.close();

        return post;
    }

    public Attachment extractAttachFromResultSet(String postId) throws Exception {

        conn = getCon();
        String query = "select * from attach where attachPostId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1,postId);
        ResultSet rs = statement.executeQuery();

        Attachment attachment = new Attachment();
        if (rs.next() == true) {
            attachment.setAttachId(rs.getString("attachId"));
            attachment.setPostId(rs.getString("attachPostId"));
            attachment.setFileName(rs.getString("fileName"));
            attachment.setFileType(rs.getString("fileType"));
            attachment.setFileSize(rs.getLong("fileSize"));
            attachment.setBlob(rs.getBlob("fileBlob"));
        }
        //post.setAttachBlob( rs.getBlob("attachment") );
        conn.close();
        return attachment;
    }

    public void insertAttach(Attachment attachment) throws Exception {

        conn = getCon();
        String attachId = attachment.getAttachId();
        String postId = attachment.getPostId();
        String fileName = attachment.getFileName();
        String fileType = attachment.getFileType();
        long fileSize = attachment.getFileSize();
        Blob blob = attachment.getBlob();

        String query = "INSERT INTO attach(attachId, attachPostId, fileName, fileType, fileSize, fileBlob) value(?,?,?,?,?,?)";
        PreparedStatement statement = conn.prepareStatement(query);

        statement.setString(1,attachId);
        statement.setString(2,postId);
        statement.setString(3,fileName);
        statement.setString(4,fileType);
        statement.setLong(5,fileSize);
        statement.setBlob(6,blob);
        statement.execute();
        statement.close();
        conn.close();

    }

    /*public boolean updateAttach(Attachment attachment) throws Exception {

        conn = getCon();

        String query = "UPDATE attach SET fileName=?, fileType=?, fileSize=?, fileBlob = ? WHERE attachId=? ";
        PreparedStatement ps = conn.prepareStatement(query);

        ps.setString(1, attachment.getFileName());
        ps.setString(2, attachment.getFileType());
        ps.setLong(3, attachment.getFileSize());
        ps.setBlob(4, attachment.getBlob());
        ps.setString(5, attachment.getAttachId());

        int i = ps.executeUpdate();

        conn.close();

        if(i == 1) {
            return true;
        }

        return false;
    }*/

    public boolean deleteAttach(String postId) throws Exception {

        conn = getCon();

        String query = "DELETE FROM attach WHERE attachPostId = ?";
        PreparedStatement statement = conn.prepareStatement(query);
        statement.setString(1, postId);

        int i = statement.executeUpdate();

        if(i == 1) {
            return true;
        }

        conn.close();

        return false;
    }

    public byte[] getAttachData(String postId) throws Exception {
        Attachment attachment = extractAttachFromResultSet(postId);
        Blob blob = attachment.getBlob();
        byte byteArray[] = blob.getBytes(1,(int)blob.length());
        return byteArray;
    }

    public String getPostTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = formatter.format(now);
        return date;
    }

    public List<Post> getPostsByHashTag(String inputTag, List<Post> posts){

        List<Post> selectedPosts = new ArrayList<>();

        for (int i=0; i< posts.size(); i++){
            String content = posts.get(i).getContent();
            String[] tags = getHashTags(content);
            for (int j=0; j< tags.length; j++){
                if (tags[j].toLowerCase().contains(inputTag)){
                    selectedPosts.add(posts.get(i));
                    break;
                }
            }
        }
        return selectedPosts;
    }

    public List<Post> getPostsByUserId(String userId, List<Post> posts){

        List<Post> selectedPosts = new ArrayList<>();

        for (int i=0; i< posts.size(); i++){
            if (posts.get(i).getUserId().equals(userId)){
                selectedPosts.add(posts.get(i));
            }
        }
        return selectedPosts;
    }

    public List<Post> getPostsByDate(String start, String end, List<Post> posts){

        List<Post> selectedPosts = new ArrayList<>();

        for(Post post : posts){
            if(post.compareToString((Object)start) >= 0 && post.compareToString((Object)end) <= 0)
                selectedPosts.add(post);
        }
        return selectedPosts;
    }

    public String[] getHashTags(String content){

        String[] split = content.split("\\#");
        String[] tags = new String[split.length-1];

        //String[] tags = Arrays.copyOfRange(oldArr, 1, oldArr.length);
        for (int i=1; i< split.length; i++){
            tags[i-1]= split[i];
        }
        return tags;
    }

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

}
