package com.triolance.chat.chat_app_backend.Entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class User {
    @Id
    private String id;               // MongoDB’s internal _id

    @Field("username")
    private String username;         // unique login name

    @Field("displayName")
    private String displayName;      // what shows up in chat


     // store only a hash!
    private String password;

    /**
     * The single room the user is currently in (null if none).
     */


    @Field("createdAt")
    private Instant createdAt = Instant.now();

    @Field("lastActiveAt")
    private Instant lastActiveAt = Instant.now();

}
