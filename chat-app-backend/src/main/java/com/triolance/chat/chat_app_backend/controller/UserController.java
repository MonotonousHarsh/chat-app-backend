package com.triolance.chat.chat_app_backend.controller;

import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.JwtUtils.JwtUtils;
import com.triolance.chat.chat_app_backend.service.UserService;
import com.triolance.chat.chat_app_backend.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/User")
//@CrossOrigin(origins = "http://localhost:5174")
public class UserController {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;


       @PostMapping("/signup")
    public ResponseEntity<User>SignUp(@RequestBody User user) {
       try {
           User saved = userService.saveUser(user);
           System.out.println("check kro error kko " + saved);
           return ResponseEntity
                   .status(HttpStatus.CREATED)
                   .body(saved);
       }catch(Exception e){
           System.out.println("this error a rha hai signup m" + e);
           // log.error("ye error in signup" , e);
       }
       return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody User user){
        try {
            String usernameFromFrontend = user.getUsername();
            String passwordFromFrontend = user.getPassword();

            UserDetails userDetails = userServiceImpl.loadUserByUsername(usernameFromFrontend);
            if (passwordEncoder.matches(passwordFromFrontend, userDetails.getPassword())) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null
                        , userDetails.getAuthorities());

                // generate a jwt

                String jwt = jwtUtils.generateToken(userDetails.getUsername());
                return ResponseEntity.ok()
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                        .body("Login successful");
            }
        }catch(UsernameNotFoundException e){
            System.out.println("this error come login kkrte samay  " + e);
            // In your method
           // log.debug("Login attempt for user: {}", user.getUsername());
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("user not found");

        }
return new ResponseEntity<>("Username or password Something wrong " , HttpStatus.FORBIDDEN);
    }




}
