package com.concordia.message_board.service;

import com.concordia.message_board.entities.Post;

import java.util.List;

public interface UserManager {
    public List<String> getList(String status);
    public void initializeFactory(String filePath);
    public List<Post> getSortedListForGroup(List<Post> list,String group);
}
