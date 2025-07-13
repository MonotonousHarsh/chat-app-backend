package com.triolance.chat.chat_app_backend.controller;


import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/room")
public class RoomController {

//    @Autowired
//    private Room room;


    @Autowired
    private RoomService roomService;

    @Autowired
    private User user;


    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(RequestBody Room ){

return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
