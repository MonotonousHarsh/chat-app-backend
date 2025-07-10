package com.triolance.chat.chat_app_backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.lang.annotation.Documented;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Room_collection")
public class Room {

    @Id
    private ObjectId id;

    @Field("roomId")
    private String roomId;

    @Field("messages")
    private List<Message>messages = new ArrayList<>();

    public static class Message{

        @Field("username")
        private String username;

        @Field("content")
        private String content;

        private Instant timestamp;


    }

        public static void addMessage(Message message){

        }




}
