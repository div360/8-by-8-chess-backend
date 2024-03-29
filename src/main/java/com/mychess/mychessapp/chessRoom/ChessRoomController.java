package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.chessData.ChessData;
import com.mychess.mychessapp.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        var player1Color = chessRoom.getPlayer1().getColor();
        Map<String, String> response = new HashMap<>();
        response.put("roomId", roomId);
        response.put("player1Id", player1Id);
        response.put("player2Id", player2Id);
        response.put("player1Color", player1Color);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/join")
    public ResponseEntity<Map<String, String>> joinChessRoom(@RequestBody Map<String, String> request) {
        if(!chessRoomService.checkIfRoomExists(request.get("roomId"))){
            return ResponseEntity.badRequest().build();
        }
        Map<String, String> response = new HashMap<>();

        var roomId = request.get("roomId");
        var playerId = request.get("playerId");
        var color = chessRoomService.getColor(roomId, playerId);
        if(request.containsKey("name")){
            response = chessRoomService.updatePlayerName(roomId, playerId, request.get("name"));
        }
        response.put("status", "success");
        response.put("message", "Joined room successfully");
        response.put("player2Color", color);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/checkroom/{roomId}")
    public ResponseEntity<Map<String, String>> checkIfRoomExists(@PathVariable String roomId){
        Map<String, String> response = new HashMap<>();
        if(chessRoomService.checkIfRoomExists(roomId)){
            response.put("status", "success");
            response.put("message", "Room exists");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Room does not exist");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/api/updateChessData")
    private ResponseEntity<Map<String, String>> updateChessData(@RequestBody ChessRoom request){
        Map<String, String> response = new HashMap<>();
        String roomId = request.getRoomId();
        ChessData chessData = request.getChessData();
        if(chessRoomService.updateChessData(roomId, chessData)){
            response.put("status", "success");
            response.put("message", "Chess data updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Chess data could not be updated");
        return ResponseEntity.badRequest().body(response);
    }

    @GetMapping("/api/getChessData/{roomId}")
    public ResponseEntity<ChessRoom> getRoomData(@PathVariable String roomId){
        ChessRoom chessRoom = chessRoomService.getChessData(roomId);
        return ResponseEntity.ok().body(chessRoom);
    }

}
