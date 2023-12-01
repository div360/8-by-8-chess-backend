package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.player.Player;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chessRoom")
public class ChessRoom {
    @Id
    private String roomId;
    private Player player1;
    private Player player2;
}
