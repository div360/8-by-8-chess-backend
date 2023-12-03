package com.mychess.mychessapp.config;

import com.mychess.mychessapp.ChessMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
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

    @Autowired
    private ChessMessageService chessMessageService;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        String roomId = "";
        try {
            assert sessionId != null;
            roomId = sessionRoomMap.get(sessionId);
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

        String roomId = "";
        try {
            assert destination != null;
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        assert sessionId != null;
        if (roomClients.containsKey(roomId)) {
            if(sessionRoomMap.containsKey(sessionId)){
                return;
            }
            sessionRoomMap.put(sessionId, roomId);
            roomClients.put(roomId, roomClients.get(roomId) + 1);
            if(roomClients.get(roomId) == 2) chessMessageService.sendGameStart(roomId);
        } else {
            sessionRoomMap.put(sessionId, roomId);
            roomClients.put(roomId, 1);
        }
    }

    @EventListener
    public void handleUnsubscribeListener(SessionUnsubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination  = headerAccessor.getDestination();

        String roomId = "";
        try {
            assert destination != null;
            roomId = destination.substring(destination.lastIndexOf('/') + 1);
        } catch (Exception e) {
            System.out.println("Room id not found");
        }

        if (roomClients.containsKey(roomId)) {
            roomClients.put(roomId, roomClients.get(roomId) - 1);
            if(roomClients.get(roomId) == 0) roomClients.remove(roomId);
        }
    }

    public static int getNumberOfClients(String roomId) {
        if (roomClients.containsKey(roomId)) {
            return roomClients.get(roomId);
        }
        return 0;
    }
}
