package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import com.sessiontask.demo.utils.SessionKeeper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;


@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;


   /* @GetMapping("/noticeList")
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "noticeList";
    }*/

    @GetMapping("/newNotice")
    public String addNewNotice(@RequestParam int id, Model model) {
        TheUser user = userService.getUser(id);
        model.addAttribute("user", user);
        return "addNotice";
    }

    @GetMapping("/notice/image/{id}")
    public void showProductImage(@PathVariable int id,
                                 HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg"); // Or whatever format you wanna use
        Notice entry = noticeService.getNotice(id);
        InputStream is = new ByteArrayInputStream(entry.image);
        IOUtils.copy(is, response.getOutputStream());
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewNotice(HttpSession session, HttpServletResponse response, @RequestParam String title, String content, MultipartFile image, int id) throws IOException {
        if (SessionKeeper.getInstance().checkSession(session.getId())) {
           TheUser user = userService.getUser(SessionKeeper.getInstance().getUserSession().id);
            Notice notice = new Notice();
            notice.title = title;
            notice.content = content;
            notice.published = new Date();
            notice.userId = user;


            if (image != null) {
                notice.image = image.getBytes();
            }

            noticeService.addNotice(notice);
        }
            response.sendRedirect("/");
        }

        @GetMapping("/edit")
        public String editNotice ( @RequestParam int id, Model model){
            Notice notice = noticeService.getNotice(id);
            model.addAttribute("notice", notice);
            return "editNotice";
        }

        @GetMapping("/addComment")
        public String commentOnNotice ( @RequestParam int id, Model model){
            Notice comment = noticeService.getNotice(id);
            model.addAttribute("notice", comment);
            return "addComment";
        }


        @RequestMapping(value = "/save", method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public void saveChanges (HttpServletResponse response,@RequestParam int id, String title, String
        content, MultipartFile image) throws IOException {

            Notice notice = noticeService.getNotice(id);
            notice.title = title;
            notice.content = content;
            notice.published = new Date();

            if (image != null) {
                notice.image = image.getBytes();
            }


            noticeService.updateNotice(notice);
            response.sendRedirect("/");
        }

        @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
        public void deleteNotice (HttpServletResponse response,@RequestParam int id) throws IOException {
            Notice notice = noticeService.getNotice(id);
            noticeService.deleteNotice(id);
            response.sendRedirect("/");
        }
    }


