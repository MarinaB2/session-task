package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.services.NoticeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;


@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


   /* @GetMapping("/noticeList")
    public String listNotices(Model model) {
        model.addAttribute("notices", noticeService.getAllNotices());
        return "noticeList";
    }*/

    @GetMapping("/newNotice")
    public String addNewNotice() {

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
    public void addNewNotice(HttpServletResponse response, @RequestParam String title, String content, MultipartFile image, TheUser userId) throws IOException {
        Notice notice = new Notice();
        notice.title = title;
        notice.content = content;
        notice.published = new Date();


        if (image != null) {
            notice.image = image.getBytes();
        }

        noticeService.addNotice(notice);
        response.sendRedirect("/");
    }

    @GetMapping("/edit")
    public String editNotice(@RequestParam int id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "editNotice";
    }

    @GetMapping("/addComment")
    public String commentOnNotice(@RequestParam int id, Model model) {
        Notice comment = noticeService.getNotice(id);
        model.addAttribute("notice", comment);
        return "addComment";
    }


    @RequestMapping(value = "/save", method = {RequestMethod.POST}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void saveChanges(HttpServletResponse response, @RequestParam int id, String title, String content, MultipartFile image) throws IOException {

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
    public void deleteEntry(HttpServletResponse response, @RequestParam int id) throws IOException {
        Notice notice = noticeService.getNotice(id);
        noticeService.deleteNotice(id);
        response.sendRedirect("/");
    }
}


