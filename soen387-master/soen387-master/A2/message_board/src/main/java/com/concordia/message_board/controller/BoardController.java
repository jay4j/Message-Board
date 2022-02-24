package com.concordia.message_board.controller;

import com.concordia.message_board.entities.Post;
import com.concordia.message_board.mapper.MessageMapper;
import com.concordia.message_board.service.PostManager;
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

    @Value("${display.number}")
    private String number;

    @GetMapping("/")
    public String logIn(){
        return "login";
    }

    //-----------------------set logout controller---------------------------
    @GetMapping("/logout")
    public String logOut(HttpSession session){
        session.setAttribute("userId",null);
        return "logout";
    }

    @PostMapping("/authentication")
    public String authentication(@RequestParam("userId") String userId,
                                 @RequestParam("password") String password,
                                 Model model, HttpSession session) throws Exception {

        if(postManager.authentication(userId, password)){
            session.setAttribute("userId",userId);
            // show post history

            messageMapper = new MessageMapper();
            List<Post> posts = messageMapper.getUserPost(userId);
            Collections.sort(posts);
            model.addAttribute("posts", posts);
            return "redirect:/postMessage.html";
        }
        return "error";
    }


    /*@RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        if (file.isEmpty()){
            model.addAttribute("uploadMessage", "The file is empty!");
            return "postMessage";
        }
        try{
            byte[] bytes = file.getBytes();
            //Path path = Paths.get("E:\\fileUpload/" + file.getOriginalFilename());
            Path path = Paths.get("C:\\Users\\Administrator\\Desktop\\SOEN387\\A2\\fileUpload/" + file.getOriginalFilename());
            Files.write(path, bytes);
            model.addAttribute("uploadMessage", "success");

        }catch (Exception e){
            e.printStackTrace();
        }
        return "postMessage";
    }*/
}
