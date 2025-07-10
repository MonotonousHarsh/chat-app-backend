package com.triolance.chat.chat_app_backend.Entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "users")
public class User {

    @Id
    private Object id;

    @NonNull
    private String username;

    @NonNull
    private  String password;

    @NonNull
    private String email;

}
