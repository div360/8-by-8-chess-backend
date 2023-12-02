package com.mychess.mychessapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;

@Component
public class RoomSubscriptionInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(@NotNull  Message<?> message, @NotNull MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        String destination  = headerAccessor.getDestination();

        if(destination == null) {
            return message;
        }
        if(!destination.contains("/topic")){
            return message;
        }

        String roomId = "";
        try {
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        int clientsInRoom = WebSocketEventListener.getNumberOfClients(roomId);

        if(clientsInRoom >= 2) {
            return null;
        }
        return message;
    }
}
