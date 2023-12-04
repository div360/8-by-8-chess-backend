package com.mychess.mychessapp;

import com.mychess.mychessapp.chessRoom.ChessRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ChessMessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    public void sendGameStart(String roomId) {
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Start Game");
        var chessMessage = ChessMessage.builder()
                .code(100)
                .roomId(roomId)
                .message(response)
                .build();

        simpMessagingTemplate.convertAndSend("/topic/" + roomId, chessMessage);
    }
}
