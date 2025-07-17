package com.triolance.chat.chat_app_backend.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.Message;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
@Document(collection = "Room_collection")
public class Room {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Field("roomId")
    private String roomId;

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    private String hostUsername;


    @Field("participants")
    private Set<String> participants = new HashSet<>();

    public void addParticipants(String username){
           participants.add(username);
    }

    public void removeParticipants(String username){
        participants.remove(username);
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public String getRoomId() {
        return roomId;
    }

    public ObjectId getId() {
        return id;
    }
}
