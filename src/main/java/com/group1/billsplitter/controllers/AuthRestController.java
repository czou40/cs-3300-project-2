package com.group1.billsplitter.controllers;

import com.group1.billsplitter.entities.Result;
import com.group1.billsplitter.entities.requests.CreateUserRequest;
import com.group1.billsplitter.entities.requests.Message;
import com.group1.billsplitter.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthRestController {
    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    private ResponseEntity<Message> signup(@RequestBody CreateUserRequest body) {
        String email = "", password = "", name = "";
        try {
            email = body.getEmail().trim();
            password = body.getPassword();
            name = body.getName().trim();
        } catch (Exception e) {
            return new ResponseEntity<>(new Message("Please specify your full name, email address, and password!"), HttpStatus.BAD_REQUEST);
        }
        if (email.equals("") || password.equals("") || name.equals("")) {
            return new ResponseEntity<>(new Message("Please specify your full name, email address, and password!"), HttpStatus.BAD_REQUEST);
        }
        if (!email.endsWith("@gatech.edu")) {
            System.out.println(email);
            return new ResponseEntity<>(new Message("You can only register with a Georgia Tech email!"), HttpStatus.BAD_REQUEST);
        } else {
            Result result = authService.CreateUser(email, password, name);
            return new ResponseEntity<>(new Message(result.getMessage()), result.isSuccessful() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        }
    }
}