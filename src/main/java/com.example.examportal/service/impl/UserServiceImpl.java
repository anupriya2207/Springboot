package com.example.examportal.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.examportal.dao.RoleDao;
import com.example.examportal.dao.UserDao;
import com.example.examportal.model.User;
import com.example.examportal.model.UserPatch;
import com.example.examportal.model.UserRole;
import com.example.examportal.service.UserService;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    //Creating a User
    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User existingUser = userDao.findByusername(user.getUsername());
        if(existingUser !=null)
        {
            System.out.println("User already present");
            throw new Exception("User already present");
        }
        else {
            for(UserRole ur: userRoles)
            {
                roleDao.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            existingUser =this.userDao.save(user);
        }
        return existingUser;
    }

    @Override
    public User getUser(String username) {

        return userDao.findByusername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public void deleteUser(String username) {
        User user =userDao.findByusername(username);
        userDao.delete(user);
    }

    @Override
    public User updateUser(String username, UserPatch userPatch) {
        User user =userDao.findByusername(username);
        if(userPatch.getEmail()!=null)
        {
            user.setEmail(userPatch.getEmail());
        }
        if(userPatch.getLastname()!=null)
        {
            user.setLastname(userPatch.getLastname());
        }
        if(userPatch.getPassword()!=null)
        {
            user.setPassword(userPatch.getPassword());
        }
        userDao.save(user);
        return user;
    }
}

