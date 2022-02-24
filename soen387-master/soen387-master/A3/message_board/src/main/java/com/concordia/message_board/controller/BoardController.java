package com.concordia.message_board.controller;

import com.concordia.message_board.entities.Post;
import com.concordia.message_board.mapper.MessageMapper;
import com.concordia.message_board.service.PostManager;
import com.concordia.message_board.service.UserFactory;
import com.concordia.message_board.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
public class BoardController {
    @Autowired
    private PostManager postManager;
    private MessageMapper messageMapper;
    @Autowired
    private UserManager userManager;

    @Value("${display.number}")
    private String number;

    @GetMapping("/")
    public String logIn(){
        userManager.initializeFactory("userGroup.xml");
        return "login";
    }

    //-----------------------set logout controller---------------------------
    @GetMapping("/logout")
    public String logOut(HttpSession session) {
        session.setAttribute("userId", null);
        return "logout";
    }

    @PostMapping("/authentication")
    public String authentication(@RequestParam("userId") String userId,
                                 @RequestParam("password") String password,
                                 Model model, HttpSession session) throws Exception {

        if (postManager.authentication(userId, password)) {
            session.setAttribute("userId", userId);
            session.setAttribute("membership", UserFactory.getMap().get(userId));
            System.out.println(session.getAttribute("membership")+"&&&&&&&&&&&&&&");
            // show post history

            messageMapper = new MessageMapper();
            List<Post> posts = messageMapper.getUserPost(userId);

            String membership = (String)session.getAttribute("membership");

            if(membership.equals("admins")){
                posts = messageMapper.getAllPost();
            }
            Collections.sort(posts);
            model.addAttribute("posts", posts);

            model.addAttribute("greeting", "Welcome,"+session.getAttribute("userId"));
           // model.addAttribute("userId", userId);
            return "postMessage";
        }
        return "error";
    }

}