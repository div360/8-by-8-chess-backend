package com.mychess.mychessapp;

import lombok.*;

import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChessMessage {
    private String messageId;
    private String roomId;
    private String senderId;
    private HashMap<String, String> message;
    private Date timestamp;
}
