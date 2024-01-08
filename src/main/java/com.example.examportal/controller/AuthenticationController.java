package com.example.examportal.controller;

import com.example.examportal.config.JwtUtils;
import com.example.examportal.dao.dto.LoginRequest;
import com.example.examportal.dao.dto.LoginResponse;
import com.example.examportal.model.User;
import com.example.examportal.service.impl.UserDetailsImpl;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsImpl userDetails;

    @Autowired
    JwtUtils jwtUtils;

    //    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/")
    public ResponseEntity<?> generateToken(@RequestBody LoginRequest loginRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),
                loginRequest.getPassword()));
            final UserDetails user= userDetails.loadUserByUsername(loginRequest.getUserName());
            String jwt = jwtUtils.generateToken(user);
            return ResponseEntity.ok().body(new LoginResponse(jwt));
        } catch (RuntimeException badCredentialsException) {
            System.out.println(badCredentialsException.getMessage());
        }
        return  ResponseEntity.badRequest().body("Error");
    }

    @GetMapping("/")
    public User getCurrentUser(Principal principal)
    {
        return (User) this.userDetails.loadUserByUsername(principal.getName());
    }
}