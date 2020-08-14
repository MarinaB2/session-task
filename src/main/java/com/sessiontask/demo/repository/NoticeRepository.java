package com.sessiontask.demo.repository;


import com.sessiontask.demo.models.Notice;
import com.sessiontask.demo.models.TheUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    Notice getById(int id);
}

