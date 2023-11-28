package com.mychess.mychessapp.chessRoom;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChessRoom {
    @Id
    private String roomId;
    private String senderId;
    private String receiverId;
}
