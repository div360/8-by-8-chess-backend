package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.player.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        } while (player1.getId() == player2.getId());

        var chessRoom = ChessRoom.builder()
                .roomId(roomId)
                .player1(player1)
                .player2(player2)
                .build();
        chessRoomRepository.save(chessRoom);
        return chessRoom;
    }
}
