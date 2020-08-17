package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.repository.UserRepository;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import com.sessiontask.demo.utils.SessionKeeper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new TheUser());
        return "login";
    }

    @GetMapping("/registerPage")
    public String registerPage(Model model) {
        model.addAttribute("user", new TheUser());
        return "register";
    }

   @PostMapping(value = "/register")
    public void addNewUser(HttpServletResponse response, @RequestParam String username, String userpassword) throws IOException {
       TheUser user = new TheUser();
       user.username = username;
       user.userpassword = userpassword;


       userService.addUser(user);
       response.sendRedirect("login");
   }

  /*  @PostMapping ("/registerNew")
    public void register(@ModelAttribute("user") TheUser user, HttpServletResponse response, String username, String userpassword) throws IOException {
        var allUsers = userService.getAllUsers();

       var userExists = allUsers
                .stream()
                .anyMatch(x -> x.getUsername().equals(user.getUsername()));

    if(!userExists){
        user.getUsername();
     user.getUserpassword();
    userRepository.save(user);
    response.sendRedirect("login");
    }*/
          //  user.setUserUsername(user.getUsername());
         //   user.setUserPassword(user.getPassword());
   // }

   @RequestMapping("logIN")
   public void logIN(HttpServletResponse response, HttpSession session, @RequestParam String username, String userpassword) throws IOException{
        if(username.equals("admin") && userpassword.equals("admin")){
            SessionKeeper.getInstance().addSession(session.getId());

           session.setMaxInactiveInterval(600);//logout automatically if the user not active for 10 minuets

        }

       response.sendRedirect("/");
        }


    //This method dosn't work, the value is always false it doesn't get the right input of some reason
   @PostMapping("logIn")
   public void logIn(@ModelAttribute("user") TheUser user, HttpServletResponse response, HttpSession session, @RequestParam String username, String userpassword) throws IOException{
      List<TheUser> allUsers = userService.getAllUsers();

    /* boolean validLogin = allUsers
              .stream()
              .anyMatch(x -> x.getUsername().equals(user.getUsername())
                      && x.getUserpassword().equals(user.getUserpassword()));*/
      for(TheUser theUser: allUsers){
          if(theUser.getUsername().equals(username) && theUser.getUserpassword().equals(userpassword)){
              SessionKeeper.getInstance().addSession(session.getId());
                 SessionKeeper.getInstance().addUserSession(user);
//              session.setMaxInactiveInterval(600);
              //logout automatically if the user not active for 10 minuets
          }
      }
     /*  if(validLogin){
           SessionKeeper.getInstance().addSession(session.getId());
    //       SessionKeeper.getInstance().addUserSession(user);
           session.setMaxInactiveInterval(600);//logout automatically if the user not active for 10 minuets
       }*/
      response.sendRedirect("/");
   }

   private boolean checkUserSession(String sessionId){
       return SessionKeeper.getInstance().checkSession(sessionId);
   }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
       session.setMaxInactiveInterval(15);
        session.invalidate();
        model.addAttribute("notices", noticeService.getAllNotices());
       return "index";
    }


}

