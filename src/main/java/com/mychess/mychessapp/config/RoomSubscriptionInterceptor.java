package com.mychess.mychessapp.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
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
            System.out.println("Destination is null");
            return message;
        }
        if(!destination.contains("/topic")){
            System.out.println("Destination is not a topic");
            return message;
        }

        System.out.println("Destination: " + destination);
        String roomId = "";
        try {
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        System.out.println("Room id: " + roomId);

        int clientsInRoom = WebSocketEventListener.getNumberOfClients(roomId);

        System.out.println("Number of clients in room: " + clientsInRoom);

        if(clientsInRoom >= 2) {
            System.out.println("Room is full");
            return null;
        }

        System.out.println("Room is not full");

        return message;
    }
}
