package com.triolance.chat.chat_app_backend.service;

import com.mongodb.DuplicateKeyException;
import com.triolance.chat.chat_app_backend.Entity.Room;
import com.triolance.chat.chat_app_backend.Entity.User;
import com.triolance.chat.chat_app_backend.repository.RoomRepository;
import com.triolance.chat.chat_app_backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


    @Transactional
    public  String CreateRoom(String roomId , String creatorUsername){


        if (roomRepository.existsByRoomId(roomId)) {
            return "Room ID already exists. Please choose a different ID.";
        }

        User creator = userRepository.findByUsername(creatorUsername);
                //.orElseThrow(()-> new RuntimeException("user not found"));
        if(creator==null){
            throw new RuntimeException("user not found");
        }

        //Add host role
        if(!user.getRoles().contains("host")){
            List<String> updatedRoles = new ArrayList<>(creator.getRoles());
            updatedRoles.add("Host");
        }
        // check roomId already Exist ??

        try {
            Room newRoom = new Room();
            newRoom.setRoomId(roomId);
            newRoom.addParticipants(creatorUsername);
            newRoom.setHostUsername(creatorUsername);
         //   user.setRoles(Collections.singletonList("Host"));

            roomRepository.save(newRoom);
            userRepository.save(user);
            return "room created successfully ! with roomId " + " " + roomId;
        } catch (DuplicateKeyException e) {
         //   log.error("this error occur while creating room",e);
            return "Room creation failed. Room ID must be unique.";
        }
    }


    public String joinRoom(String roomId,String username){

        Room room = roomRepository.findByRoomId(roomId);

        if(room == null){
            return "room Not Found"+roomId;

        }

        if(!room.getParticipants().contains(username)){
            room.addParticipants(username);
        roomRepository.save(room);
        }

        room.addParticipants(username);
        roomRepository.save(room);
        return "You Joined Room Successfully";

    }
}
