package com.triolance.chat.chat_app_backend.repository;

import com.triolance.chat.chat_app_backend.Entity.Room;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, ObjectId> {

    Room findByRoomId(String roomId);
}
