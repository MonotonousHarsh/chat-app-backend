package com.triolance.chat.chat_app_backend.service;


import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private User user;



    public User saveUser (User user){
        userRepository.save(user);
        return user;
    }

    public User UpdateUserProfile(User user){
      String Updatedusername = user.getUsername();
      user.setUsername(Updatedusername);
      user.setCreatedAt(Instant.now());
      user.setEmail(user.getEmail());
      return user;
    }


    public void DeleteUser(){
       userRepository.deleteById(user.getId());
    }


}
