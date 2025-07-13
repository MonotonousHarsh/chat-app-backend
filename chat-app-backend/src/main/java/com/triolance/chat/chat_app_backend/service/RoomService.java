package com.triolance.chat.chat_app_backend.service;

import com.mongodb.DuplicateKeyException;
import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.repository.RoomRepository;
import com.triolance.chat.chat_app_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Slf4j
@Service
public class RoomService {

    @Autowired
    private Room room;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private User user;


    public  String CreateRoom(String roomId , String creatorUsername){

        // check roomId already Exist ??
        if(roomRepository.findByRoomId(roomId).equals(roomId) && roomRepository.findByRoomId(roomId) != null){
            return "Room Id already exist . please Choose different";
        }
        try {
            Room newRoom = new Room();
            newRoom.setRoomId(roomId);
            newRoom.addParticipants(creatorUsername);
            user.setRoles(Collections.singletonList("Host"));

            roomRepository.save(newRoom);
            userRepository.save(user);
            return "room created successfully ! with roomId " + " " + roomId;
        } catch (DuplicateKeyException e) {
            log.error("this error occur while creating room",e);
            return "Room creation failed. Room ID must be unique.";
        }
    }


    public String joinRoom(String roomId,String username){

        Room room = roomRepository.findByRoomId(roomId);

        if(room == null){
            return "room Not Found" ;

        }

        if(room.getParticipants().contains(username)){
            return " You already present inside room";

        }

        room.addParticipants(username);
        roomRepository.save(room);
        return "You Joined Room Successfully";

    }
}
