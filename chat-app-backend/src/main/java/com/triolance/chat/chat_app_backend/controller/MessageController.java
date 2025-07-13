package com.triolance.chat.chat_app_backend.controller;


import com.triolance.chat.chat_app_backend.Entity.Message;
import com.triolance.chat.chat_app_backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private Message message;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/{roomId}/send")
    @SendTo("/topic/room/{roomId}")
    public Message handleMessage(@DestinationVariable String roomId,
                                 Message message , Principal principal){

        message.setSender(principal.getName());
        message.setRoomId(roomId);
        messageService.saveMesssage(message);
        return message;
    }

    // Handles User joining room
    @MessageMapping("/chat/{roomId}/join")
    public void handleUserJoined(@DestinationVariable String roomId,
                                 Principal principal){
    String username = principal.getName();


    // Notify others about new User

        Message joinMessage = new Message();
        joinMessage.setRoomId(roomId);
        joinMessage.setSender("system");
        joinMessage.setContent(username + " joined the room ");
        messagingTemplate.convertAndSendToUser("/topic/room/" + roomId,  username,joinMessage);
        // Send message history to the new user
        List<Message> history = messageService.getMessageHistory(roomId);
        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/room/" + roomId + "/history",
                history
        );

    }

}
