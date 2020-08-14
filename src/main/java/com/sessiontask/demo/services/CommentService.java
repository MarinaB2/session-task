package com.sessiontask.demo.services;

import com.sessiontask.demo.models.Comment;
import com.sessiontask.demo.repository.CommentRepository;
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
   CommentRepository commentRepository;

    public CommentService(){

    }
    public Comment getComment(int id) {
        return commentRepository.findById(id).get();
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "published"));
    }

    public void deleteComments(int id) {

        commentRepository.deleteById(id);
    }

    public void addComments(Comment comment) {

        commentRepository.save(comment);
    }

    public void updateComment(Comment comment) {

        commentRepository.save(comment);
    }



}
