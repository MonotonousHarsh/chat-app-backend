package com.triolance.chat.chat_app_backend.repository;

import com.triolance.chat.chat_app_backend.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;



public interface UserRepository extends MongoRepository<User, String > {



 User findByUsername(String username);
}
