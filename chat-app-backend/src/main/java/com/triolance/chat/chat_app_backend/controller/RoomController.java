package com.triolance.chat.chat_app_backend.controller;

import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger log = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        try {
            String createRoomResult = roomService.CreateRoom(room.getRoomId(), authentication.getName());
            return new ResponseEntity<>(createRoomResult, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error creating room", e);
            return new ResponseEntity<>("Error creating room: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/join-room")
    public ResponseEntity<?> joinRoom(@RequestBody Map<String, String> request) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String roomId = request.get("roomId");
            String result = roomService.joinRoom(roomId, username);

            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "message", result
            ));
        } catch (Exception e) {
            log.error("Error joining room", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}