package com.example.examportal.dao;

import com.example.examportal.model.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {

      User findByusername(String username);
}
