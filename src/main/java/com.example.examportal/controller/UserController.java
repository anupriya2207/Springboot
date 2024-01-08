package com.example.examportal.controller;

import com.example.examportal.model.Role;
import com.example.examportal.model.User;
import com.example.examportal.model.UserPatch;
import com.example.examportal.model.UserRole;
import com.example.examportal.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
        Set<UserRole> userRoleSet = new HashSet<>();
        Role role = new Role("ADMIN");
        UserRole userRole = new UserRole(user,role);
        userRoleSet.add(userRole);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return this.userService.createUser(user,userRoleSet);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username")String username)
    {
        return this.userService.getUser(username);
    }

    @GetMapping("/getusers")
    public List<User> getAllUsers()
    {
        return this.userService.getAllUsers();
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username)
    {
        this.userService.deleteUser(username);
    }

    @PutMapping("/{username}")
    public User updateUser(@PathVariable("username") String username, @RequestBody UserPatch userPatch)
    {
        return this.userService.updateUser(username,userPatch);
    }
}
