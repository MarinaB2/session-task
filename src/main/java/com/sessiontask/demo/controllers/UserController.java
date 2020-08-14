package com.sessiontask.demo.controllers;

import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.services.NoticeService;
import com.sessiontask.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


}
