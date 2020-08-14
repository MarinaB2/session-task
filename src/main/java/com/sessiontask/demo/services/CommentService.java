package com.sessiontask.demo.services;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.repository.CommentRepository;
import com.sessiontask.demo.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    DataSource dataSource;

    @Autowired
   CommentRepository CommentRepository;

    public CommentService(){

    }
    public Comment getComment(int id) {
        return CommentRepository.findById(id).get();
    }

    public List<Comment> getAllComments() {
        return CommentRepository.findAll(Sort.by(Sort.Direction.DESC, "published"));
    }

    public void deleteComments(int id) {

        CommentRepository.deleteById(id);
    }

    public void addComments(Comment comment) {

        CommentRepository.save(comment);
    }

    public void updateComment(Comment comment) {

        CommentRepository.save(comment);
    }



}
