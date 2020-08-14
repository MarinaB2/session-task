package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.repository.UserRepository;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import com.sessiontask.demo.utils.SessionKeeper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;


@Controller
public class viewController implements HttpSessionListener {


    public void sessionCreated(HttpSessionEvent event){
        event.getSession().setMaxInactiveInterval(15); // in seconds
    }

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

    @GetMapping("/loginn")
    public String login(Model model) {
        model.addAttribute("user", new TheUser());
        return "login";
    }

    @GetMapping("/registerPage")
    public String registerPage(Model model) {
        model.addAttribute("user", new TheUser());
        return "register";
    }
/*
   @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewUser(HttpServletResponse response, @RequestParam String username, String password) throws IOException {
        TheUser user = new TheUser();
        user.userName = username;
        user.password = password;


        userService.addUser(user);
        response.sendRedirect("/");*/

    @PostMapping ("/register")
    public void  register(@ModelAttribute("user") TheUser user, HttpServletResponse response, Model model) throws IOException {
        var allUsers = userService.getAllUsers();

       var userExists = allUsers
                .stream()
                .anyMatch(x -> x.getUserName().equals(user.getUserName()));


        if (!userExists) {
            userService.addUser(user);
            response.sendRedirect("loginn");
        } else {
           response.sendRedirect("register");
        }
    }
   @PostMapping("loginn")
   public void logIn(@ModelAttribute("user") TheUser user, HttpServletResponse response, HttpSession session) throws IOException{
       var allUsers = userRepository.findAll();

      var validLogin = allUsers.stream().anyMatch((x -> x.getUserName().equals(("admin"))  && x.getPassword().equals("admin") ));

    for(TheUser bu : allUsers){
          if(bu.getUserName().equals("admin") && bu.getPassword().equals("admin"));
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

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
       session.setMaxInactiveInterval(15);
        session.invalidate();
        model.addAttribute("notices", noticeService.getAllNotices());
       return "index";
    }


}

