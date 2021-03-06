package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.services.CommentService;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import com.sessiontask.demo.utils.SessionKeeper;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private UserService userService;

    @GetMapping("/listComments")
    public String listcomments(@RequestParam int id, Model model) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("comments", commentService.getAllComments());
        return "listComments";
    }

    @RequestMapping(value = "/comment", method = {RequestMethod.POST, RequestMethod.GET}, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addNewComment(HttpSession session, HttpServletResponse response, @RequestParam String title, String content, MultipartFile image, TheUser userId, int id) throws IOException {
        if (SessionKeeper.getInstance().checkSession(session.getId())) {

            TheUser user = userService.getUserByUsername(SessionKeeper.getInstance().getUserSession().username);
            Comment comment = new Comment();
            comment.title = title;
            comment.content = content;
            comment.published = new Date();
            comment.noticeId = noticeService.getNotice(id);
            comment.userId = user;

            if (image != null) {
                comment.image = image.getBytes();
            }
            commentService.addComments(comment);
        }
        response.sendRedirect("/");
    }
}
