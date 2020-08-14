package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/listComments")
    public String listcomments(Model model) {
        model.addAttribute("comments", commentService.getAllComments());
        return "listComments";
    }

    @RequestMapping(value = "/comment", method = {RequestMethod.POST, RequestMethod.GET}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewComment(HttpServletResponse response, @RequestParam String title, String content, MultipartFile image, TheUser userId) throws IOException {
        Comment comment = new Comment();
        comment.title = title;
        comment.content = content;
        comment.published = new Date();

        if (image != null) {
            comment.image = image.getBytes();
        }

        commentService.addComments(comment);
        response.sendRedirect("/");
    }
}