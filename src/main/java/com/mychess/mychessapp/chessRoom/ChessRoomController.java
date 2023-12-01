package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/generate")
    public ResponseEntity<Map<String, String>> generateChessRoom(@RequestBody Player player1) {
        var chessRoom = chessRoomService.generateChessRoom(player1);
        var roomId = chessRoom.getRoomId();
        var player1Id = chessRoom.getPlayer1().getId();
        var player2Id = chessRoom.getPlayer2().getId();
        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("player1Id", player1Id);
        response.put("player2Id", player2Id);
        return ResponseEntity.ok(response);
    }
}
