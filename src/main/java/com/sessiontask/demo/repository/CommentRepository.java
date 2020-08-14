package com.sessiontask.demo.repository;

import com.sessiontask.demo.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment getById(int id);
}
