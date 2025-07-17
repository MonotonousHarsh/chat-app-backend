package com.triolance.chat.chat_app_backend.controller;


import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.service.RoomService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

//    @Autowired
//    private Room room;


    @Autowired
    private RoomService roomService;

    @Autowired
    private User user;


    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@RequestBody Room room){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication==null || !authentication.isAuthenticated()){
            return new ResponseEntity<>("Unauthorised",HttpStatus.UNAUTHORIZED);
        }

        // Add this debug log
        System.out.println("AUTH DETAILS: " +
                (authentication != null
                        ? "User: " + authentication.getName() + " | Roles: " + authentication.getAuthorities()
                        : "No authentication"
                ));


        try {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if(authentication==null){
//                return new ResponseEntity<>("Unauthorised",HttpStatus.UNAUTHORIZED);

            String createRoom = roomService.CreateRoom(room.getRoomId(), authentication.getName());
            System.out.println("checkks  " + createRoom);

            return new ResponseEntity<>(createRoom, HttpStatus.CREATED);
        }catch(Exception e){
            log.error("error comes while creating Chat-room" , e);
            System.out.println("room Bnane mein error" + e);
        }return new ResponseEntity<>("error come in your RoomService,Please visit",HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PostMapping("/join-room")
    public  ResponseEntity<?> joinRoom(@RequestBody Room room){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication==null){
                return new ResponseEntity<>("Please do  Login first",HttpStatus.UNAUTHORIZED);
            }try {
                String username =   authentication.getName();
                String roomId = room.getRoomId();

            String joinedRoom = roomService.joinRoom(roomId,username);

              return ResponseEntity.ok(java.util.Map.of(
                    "status", "success",
                    "message", "Joined room: " + roomId
            ));
        }catch(Exception e){
                log.error("error arha hai!! while creating ROOM",e);
            System.out.println("check krle Room Service Ko"  + e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(java.util.Map.of("error", e.getMessage()));

        }

    }




}
