package com.triolance.chat.chat_app_backend.controller;

import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.service.UserService;
import com.triolance.chat.chat_app_backend.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/User")
public class UserController {



    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;


    @PostMapping("/signup")
    public ResponseEntity<User> SignUp(@RequestBody User user) {
        User saved = userService.saveUser(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user){
        String usernameFromFrontend = user.getUsername();
        String passwordFromFrontend = user.getPassword();

        UserDetails userDetails = userServiceImpl.loadUserByUsername(usernameFromFrontend);
        if(userDetails != null && userDetails.getPassword().equals(passwordFromFrontend)){
            return new ResponseEntity<>("User successfully Login" , HttpStatus.OK);
        }
return new ResponseEntity<>("Username or password Something wrong " , HttpStatus.FORBIDDEN);
    }




}
