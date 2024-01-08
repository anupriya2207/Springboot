package com.example.examportal.service;

import com.example.examportal.model.User;
import com.example.examportal.model.UserPatch;
import com.example.examportal.model.UserRole;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

public interface UserService {

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User getUser(String username);

    List<User> getAllUsers();

    void deleteUser(String username);

    User updateUser(String username, UserPatch userPatch);
}
