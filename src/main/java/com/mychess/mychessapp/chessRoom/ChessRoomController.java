package com.mychess.mychessapp.chessRoom;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChessRoomController {
    private final ChessRoomService chessRoomService;

    @PostMapping("/api/createroom")
    public ResponseEntity<Map<String, String>> createChessRoom(@RequestBody String userId) {
        var roomId = chessRoomService.createChessRoom(userId);
        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        return ResponseEntity.ok(response);
    }
}
