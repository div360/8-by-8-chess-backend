package com.mychess.mychessapp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChessController {
    private final ChessMessageService chessMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private SimpUserRegistry simpUserRegistry;

    // path from client: /api/subscribe/{roomId}
    // path from server: /topic/{roomId}
    // here we are subscribing to a room and checking the size of the room
    @SubscribeMapping("/topic/{roomId}")
    public void subscribe(Principal principal, @DestinationVariable String roomId) {
//        var message = "Subscribe to room " + roomId + " successfully";
        HashMap<String, String> response = new HashMap<>();
//        response.put("message", message);

        int roomSize = simpUserRegistry.findSubscriptions(sub -> sub.getDestination().equals("/topic/" + roomId)).size();
        if(roomSize == 0){
            System.out.println("Room is full");
            response.put("status", "success");
            response.put("message", "Room is full");
        }

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



}
