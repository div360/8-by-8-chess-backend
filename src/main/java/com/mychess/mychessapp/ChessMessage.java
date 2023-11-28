package com.mychess.mychessapp;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class ChessMessage {
    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String receiverId;
    private String fenString;
    private Date timestamp;
}
