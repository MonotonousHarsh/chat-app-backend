package com.triolance.chat.chat_app_backend.Entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component

@Document(collection = "users")
public class User {
    @Id
    private String id;               // MongoDB’s internal _id

    @Field("username")
    @Indexed(unique = true)
    private String username;         // unique login name

    private String email;

     // store only a hash!

    @Setter
    private String password;

    public String getPassword() {
        return this.password;
    }


    /**
     * The single room the user is currently in (null if none).
     */



    @Field("createdAt")
    private Instant createdAt = Instant.now();

    @Field("lastActiveAt")
    private Instant lastActiveAt = Instant.now();





    private List<String> Roles = new ArrayList<>();

    public List<String> getRoles() {
        return this.Roles;
    }

    public void setRoles(List<String> roles) {
        Roles = roles;
    }

    public void setLastActiveAt(Instant lastActiveAt) {
        this.lastActiveAt = lastActiveAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    public Instant getLastActiveAt() {
        return lastActiveAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='[PROTECTED]'" + // Never log actual passwords!
                // add other fields you need to see
                '}';
    }
}
