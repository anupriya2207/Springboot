package com.example.examportal.dao;

import com.example.examportal.model.Role;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role, UUID> {
}
