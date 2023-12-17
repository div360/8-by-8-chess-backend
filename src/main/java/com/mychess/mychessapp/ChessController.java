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
        var senderId = chessMessage.getSenderId();

        var newChessMessage = ChessMessage.builder()
                .code(200)
                .roomId(roomId)
                .senderId(senderId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }

    @MessageMapping("/chat/{roomId}")
    public void sendMessage(Principal principal, @DestinationVariable String roomId, @Payload ChessMessage chessMessage) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", chessMessage.getMessage().get("message"));
        var senderId = chessMessage.getSenderId();

        var newChessMessage = ChessMessage.builder()
                .code(600)
                .roomId(roomId)
                .senderId(senderId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }

    @MessageMapping("/videoChat/{roomId}")
    public void sendVideoCall(Principal principal, @DestinationVariable String roomId, @Payload ChessMessage chessMessage) {

        HashMap<String, Object> response = new HashMap<>();

        response.put("type", chessMessage.getVideoMessage().get("type"));
        response.put("data", chessMessage.getVideoMessage().get("data"));
        var senderId = chessMessage.getSenderId();

        var newChessMessage = ChessMessage.builder()
                .code(500)
                .roomId(roomId)
                .senderId(senderId)
                .videoMessage(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, newChessMessage);
    }

    @MessageMapping("/nameExchange/{roomId}")
    public void sendName(Principal principal, @DestinationVariable String roomId, @Payload ChessMessage chessMessage) {
        HashMap<String, String> response = new HashMap<>();
        response.put("name", chessMessage.getMessage().get("name"));
        var senderId = chessMessage.getSenderId();

        var newChessMessage = ChessMessage.builder()
                .code(700)
                .roomId(roomId)
                .senderId(senderId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }
}
