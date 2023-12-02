package com.mychess.mychessapp.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {
    private static final ConcurrentHashMap<String, Integer> roomClients = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String> sessionRoomMap = new ConcurrentHashMap<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String destination  = headerAccessor.getDestination();
        System.out.println("Web socket connection closed");
        System.out.println("Session id from disconnect: "+sessionId);
        System.out.println(SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders()));

        System.out.println("Destination from disconnect: "+destination);

        String roomId = "";
        try {
            assert sessionId != null;
            roomId = sessionRoomMap.get(sessionId);
            System.out.println("Room id from disconnect try block: " + roomId);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        if (roomClients.containsKey(roomId)) {
            roomClients.put(roomId, roomClients.get(roomId) - 1);
            if(roomClients.get(roomId) == 0) roomClients.remove(roomId);
        }
    }

    @EventListener
    public void handleSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination  = headerAccessor.getDestination();
        String sessionId = headerAccessor.getSessionId();
        System.out.println("New subscription event");

        String roomId = "";
        try {
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        if (roomClients.containsKey(roomId)) {
            assert sessionId != null;
            sessionRoomMap.put(sessionId, roomId);
            roomClients.put(roomId, roomClients.get(roomId) + 1);
        } else {
            assert sessionId != null;
            sessionRoomMap.put(sessionId, roomId);
            roomClients.put(roomId, 1);
        }

        System.out.println("Received a subscription event");

    }

    @EventListener
    public void handleUnsubscribeListener(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination  = headerAccessor.getDestination();
        System.out.println("Web socket connection closed");

        System.out.println("Destination from unsubscribe: ");

        String roomId = "";
        try {
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        System.out.println("Room id from unsubscribe: " + roomId);

        if (roomClients.containsKey(roomId)) {
            roomClients.put(roomId, roomClients.get(roomId) - 1);
            if(roomClients.get(roomId) == 0) roomClients.remove(roomId);
        }
    }

    public static int getNumberOfClients(String roomId) {
        System.out.println("Getting number of clients in room: " + roomId);

        if (roomClients.containsKey(roomId)) {
            return roomClients.get(roomId);
        }
        return 0;
    }
}
