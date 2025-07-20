
package com.triolance.chat.chat_app_backend.config;

import com.triolance.chat.chat_app_backend.JwtUtils.JwtUtils;
import com.triolance.chat.chat_app_backend.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class WebSocketJwtInterceptor implements ChannelInterceptor {

    private final JwtUtils jwtUtils;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public WebSocketJwtInterceptor(JwtUtils jwtUtils, UserServiceImpl userServiceImpl) {
        this.jwtUtils = jwtUtils;
        this.userServiceImpl = userServiceImpl;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) {
            return message;
        }

        StompCommand command = accessor.getCommand();

        // 1. Handle CONNECT command (authentication)
        if (StompCommand.CONNECT.equals(command)) {
            handleConnectCommand(accessor);
        }
        // 2. Handle SUBSCRIBE command (authorization)
        else if (StompCommand.SUBSCRIBE.equals(command)) {
            handleSubscribeCommand(accessor);
        }

        return message;
    }

    private void handleConnectCommand(StompHeaderAccessor accessor) {
        String token = extractToken(accessor);
        if (token == null) {
            // Allow connection without token (will fail later when subscribing)
            return;
        }

        String username = jwtUtils.ExtractUsername(token);
        if (username == null) {
            return;
        }

        // Only authenticate if security context is empty
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userServiceImpl.loadUserByUsername(username);

            if (jwtUtils.validateToken(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext().setAuthentication(authentication);
                accessor.setUser(authentication);
            }
        }
    }

    private void handleSubscribeCommand(StompHeaderAccessor accessor) {
        // Check if user is authenticated for private queues
        String destination = accessor.getDestination();
        if (destination != null && destination.startsWith("/user/")) {
            if (accessor.getUser() == null) {
                throw new SecurityException("Unauthorized subscription attempt");
            }
        }
    }

    private String extractToken(StompHeaderAccessor accessor) {
        String token = accessor.getFirstNativeHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}