package com.triolance.chat.chat_app_backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Component
@Getter
@Setter
@Document(collection = "messages")
public class Message {





    @Field("username")
    private String username;

    @Field("content")
    private String content;

    private String roomId;

    public String getUsername() {
        return username;
    }

    private String  sender;

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getSender() {
        return sender;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private Instant timestamp = Instant.now();

    @Field("messages")
    private List<Message> messages = new ArrayList<>();

    public Message(String username, String content, Instant timestamp) {
    }


    public void addMessage(String username, String content) {

        this.messages.add(new Message( username, content, timestamp));

    }


    public Message() {
        // empty constructor
    }


}