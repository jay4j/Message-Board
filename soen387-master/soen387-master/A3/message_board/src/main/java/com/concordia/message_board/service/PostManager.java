package com.concordia.message_board.service;

import com.concordia.message_board.entities.Post;
import org.springframework.stereotype.Service;

import java.util.List;

public interface PostManager {
    public boolean authentication(String username,String password);

    public String encoding(String username, String password);

    public String getPostTime();

    public void createPost(Post post);

    public List<Post> getAllPost();

    public List<Post> searchByUserId(String userId);

    public List<Post> searchByHashtag(String hashtag);

    public List<Post> searchByDate(String date);

    public void deleteByPostId(String postId);
}
