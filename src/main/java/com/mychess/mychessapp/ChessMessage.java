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
    private Integer code;
    private String roomId;
    private String senderId;
    private HashMap<String, String> message;
    private Date timestamp;
}

// Code and message:
// 100: "Start Game"
// 200: "New Move"
// 300: "Subscribed to Room"
