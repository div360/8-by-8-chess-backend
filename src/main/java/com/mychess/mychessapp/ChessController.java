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
                .code(300)
                .roomId(roomId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }

    @MessageMapping("/move/{roomId}")
    public void sendMove(Principal principal, @DestinationVariable String roomId, @Payload ChessMessage chessMessage) {
        HashMap<String, String> response = new HashMap<>();
        response.put("from", chessMessage.getMessage().get("from"));
        response.put("to", chessMessage.getMessage().get("to"));
        response.put("fen_string", chessMessage.getMessage().get("fenString"));
        var senderId = chessMessage.getSenderId();

        System.out.println("Move in room " + roomId + ": " + response);

        var newChessMessage = ChessMessage.builder()
                .code(200)
                .roomId(roomId)
                .senderId(senderId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }
}
