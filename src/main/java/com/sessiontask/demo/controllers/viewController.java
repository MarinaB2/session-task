package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.repository.UserRepository;
import com.sessiontask.demo.services.CommentService;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import com.sessiontask.demo.utils.Password;
import com.sessiontask.demo.utils.SessionKeeper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

@Controller
public class viewController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticeService noticeService;


@Autowired
private UserService userService;

    @GetMapping("/")
    public String listNotices(HttpServletResponse response, HttpSession session, Model model){
        TheUser blank = new TheUser();
        model.addAttribute("user", blank);

        if(checkUserSession(session.getId())) {
            model.addAttribute("notices", noticeService.getAllNotices());
            return "noticeList";
        } else {
            model.addAttribute("notices", noticeService.getAllNotices());
            return "index";
        }
    }
/*    @GetMapping("/")
    public String index(HttpServletResponse response, HttpSession session, Model model){
      TheUser blank = new TheUser();
      model.addAttribute("user", blank);
      if(checkUserSession(session.getId())){
          return "noticeList";
      }else {
          return "index";
      }
   }*/

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new TheUser());
        return "login";
    }

   @PostMapping("login")
   public void logIn(@ModelAttribute("user") TheUser user, HttpServletResponse response, HttpSession session) throws IOException{
       var allUsers = userRepository.findAll();

      var validLogin = allUsers.stream().anyMatch((x -> x.getUserName().equals((user.getUserName()))  && x.getPassword().equals(user.getPassword()) ));

    for(TheUser bu : allUsers){
          if(bu.getUserName().equals(user.getUserName()) && bu.getPassword().equals(user.getPassword()));
          validLogin = true;
          break;
      }
      if(validLogin){
          SessionKeeper.getInstance().AddSession(session.getId());
      }
      response.sendRedirect("/");
   }

   private boolean checkUserSession(String sessionId){
       return SessionKeeper.getInstance().CheckSession(sessionId);
   }
}

