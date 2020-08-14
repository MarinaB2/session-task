package com.sessiontask.demo.repository;



import com.sessiontask.demo.models.TheUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TheUser, Integer> {
    TheUser getById(int id);
}
