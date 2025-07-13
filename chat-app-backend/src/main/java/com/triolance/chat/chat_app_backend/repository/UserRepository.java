package com.triolance.chat.chat_app_backend.repository;

import com.triolance.chat.chat_app_backend.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId > {

 User findByUsername(String username);


}
