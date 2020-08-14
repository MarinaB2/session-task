package com.sessiontask.demo.services;

import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import com.sessiontask.demo.repository.NoticeRepository;
import com.sessiontask.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class UserService {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserRepository userRepository;

    public UserService(){

    }
    public TheUser getUser(int id) {
        return userRepository.findById(id).get();
    }








}
