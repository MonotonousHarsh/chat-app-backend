package com.triolance.chat.chat_app_backend.service;


import com.triolance.chat.chat_app_backend.Entity.Message;
import com.triolance.chat.chat_app_backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public void  saveMesssage(Message message){
        messageRepository.save(message);
    }


    public List<Message> getMessageHistory(String roomId){
      return  messageRepository.findByRoomIdOrderByTimestampAsc(roomId);
            }


}
