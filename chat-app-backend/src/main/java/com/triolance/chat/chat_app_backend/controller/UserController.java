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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/User")
//@CrossOrigin(origins = "http://localhost:5174")
public class UserController {


    @Autowired
    private AuthenticationManager authenticationManager;

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
    public ResponseEntity <Map<String,Object>> login (@RequestBody User user){
        try {
            authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(
                    user.getUsername(),user.getPassword()
            ));
            System.out.println("username come from frontend" + " "  + user.getUsername()+
                    "" + "password come from frontend" + user.getPassword());
//            String usernameFromFrontend = user.getUsername();
//            String passwordFromFrontend = user.getPassword();

            UserDetails userDetails = userServiceImpl.loadUserByUsername(user.getUsername());
            System.out.println("printing " + userDetails);
            String jwt = jwtUtils.generateToken(user.getUsername());

            System.out.println("printing JWT APNA WALA"+ "" + jwt);
            Map<String , Object > payload = Map.of(
                    "token",jwt,
                    "userDetails",userDetails

            );

                // generate a jwt


                return ResponseEntity.status(HttpStatus.CREATED).body(payload);

        }catch(UsernameNotFoundException e){
            System.out.println("this error come login kkrte samay  " + e);
            // In your method
           // log.debug("Login attempt for user: {}", user.getUsername());
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error" , " sometjing went wrong"));

        }

    }




}
