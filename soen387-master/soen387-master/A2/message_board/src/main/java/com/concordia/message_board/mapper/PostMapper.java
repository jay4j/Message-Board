package com.concordia.message_board.mapper;

import com.concordia.message_board.entities.Attachment;
import com.concordia.message_board.entities.Post;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;


import java.sql.Blob;
import java.util.ArrayList;

@Mapper
public interface PostMapper {

    @Insert("insert into post(userId,postId,postDate,title,content,attachment,edited) " +
            "values(#{userId},#{postId},#{postDate},#{title},#{content},#{attachment},#{edited})")
    public int createPost(String userId, String postId, String postDate, String title, String content, Attachment attachment, boolean edited);

    @Delete("delete from post WHERE postId = (#{postId})")
    public int deletePostByPostId(String postId);

    @Update("update post set attachment = #{attachment} WHERE postId = #{postId}")
    public int updatePost(Blob attachment);

    @Select("select * from post")
    public ArrayList<Post> getAll();
}
