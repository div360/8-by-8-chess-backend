package com.mychess.mychessapp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChessController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    // path from client: /app/subscribe/{roomId}
    // path from server: /topic/{roomId}
    @SubscribeMapping("/topic/{roomId}")
    public void subscribe(Principal principal, @DestinationVariable String roomId) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Subscribed to room " + roomId + " successfully");

        var chessMessage = ChessMessage.builder()
                .roomId(roomId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }

    @MessageMapping("/send/{roomId}")
    public void sendMessage(Principal principal, @DestinationVariable String roomId) {
        var message = "Sent to room " + roomId + " successfully";
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);

        var chessMessage = ChessMessage.builder()
                .roomId(roomId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }

    @MessageMapping("/move/{roomId}")
    public void sendMove(Principal principal, @DestinationVariable String roomId, @Payload ChessMessage chessMessage) {
        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }
}
