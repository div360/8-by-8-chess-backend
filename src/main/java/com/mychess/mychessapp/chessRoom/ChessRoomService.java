package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.chessData.ChessData;
import com.mychess.mychessapp.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ChessRoomService {
    private final ChessRoomRepository chessRoomRepository;

    public ChessRoom generateChessRoom(Player player1) {
        String roomId;
        player1.setId(UUID.randomUUID().toString());
        Player player2 = new Player();

        do {
            roomId = UUID.randomUUID().toString();
        } while (chessRoomRepository.findByRoomId(roomId).isPresent());

        do {
            player2.setId(UUID.randomUUID().toString());
        } while (player1.getId().equals(player2.getId()));

        String player1Color = player1.getColor();
        String player2Color = player1Color.equals("white") ? "black" : "white";
        player2.setColor(player2Color);

        var chessRoom = ChessRoom.builder()
                .roomId(roomId)
                .player1(player1)
                .player2(player2)
                .build();
        chessRoomRepository.save(chessRoom);
        return chessRoom;
    }

    public boolean checkIfRoomExists(String roomId){
        return chessRoomRepository.findByRoomId(roomId).isPresent();
    }

    public String getColor(String roomId, String playerId){
        Optional<ChessRoom> optionalChessRoom = chessRoomRepository.findByRoomId(roomId);
        if(optionalChessRoom.isEmpty()){
            throw new NoSuchElementException();
        }
        ChessRoom chessRoom = optionalChessRoom.get();
        if(chessRoom.getPlayer1().getId().equals(playerId)){
            return chessRoom.getPlayer1().getColor();
        } else if(chessRoom.getPlayer2().getId().equals(playerId)){
            return chessRoom.getPlayer2().getColor();
        } else {
            throw new NoSuchElementException();
        }
    }

    public HashMap<String, String> updatePlayerName(String roomId, String playerId, String name){
        Optional<ChessRoom> optionalChessRoom = chessRoomRepository.findByRoomId(roomId);
        HashMap<String, String> response = new HashMap<>();

        optionalChessRoom.ifPresent(chessRoom -> {
            if (chessRoom.getPlayer1().getId().equals(playerId)) {
                chessRoom.getPlayer1().setName(name);
            } else {
                chessRoom.getPlayer2().setName(name);
            }
            chessRoomRepository.save(chessRoom);
            response.put("roomId", chessRoom.getRoomId());
            response.put("selfId", playerId);
            response.put("opponentId", chessRoom.getPlayer1().getId().equals(playerId)
                    ?
                    chessRoom.getPlayer2().getId()
                    :
                    chessRoom.getPlayer1().getId());
        });

        return response;
    }

    public boolean updateChessData(String roomId, ChessData chessData) {
        Optional<ChessRoom> optionalChessRoom = chessRoomRepository.findByRoomId(roomId);
        if (optionalChessRoom.isPresent()) {
            ChessRoom chessRoom = optionalChessRoom.get();
            chessRoom.setChessData(chessData);
            chessRoomRepository.save(chessRoom);
            return true;
        }
        return false;
    }


    public ChessRoom getChessData(String roomId){
        Optional<ChessRoom> optionalChessRoom = chessRoomRepository.findByRoomId(roomId);
        if(optionalChessRoom.isEmpty()){
            throw new NoSuchElementException();
        }
        ChessRoom chessRoom = optionalChessRoom.get();
        return chessRoom;
    }

}
