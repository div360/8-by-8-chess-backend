package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.chessData.ChessData;
import com.mychess.mychessapp.player.Player;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "chessRoom")
public class ChessRoom {
    @Id
    private ObjectId _id;
    private String roomId;
    private Player player1;
    private Player player2;
    private ChessData chessData;
}
