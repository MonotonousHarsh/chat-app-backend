package com.triolance.chat.chat_app_backend.repository;

import com.triolance.chat.chat_app_backend.Entity.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


public interface RoomRepository extends MongoRepository<Room, ObjectId> {

    Room findByRoomId(String roomId);

   // @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r WHERE r.roomId = :roomId")
    boolean existsByRoomId(String roomId);


}
