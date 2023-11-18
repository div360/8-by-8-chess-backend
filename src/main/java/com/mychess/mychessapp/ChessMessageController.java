package com.mychess.mychessapp;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChessMessageController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChessMessage sendMessage(
            @Payload ChessMessage chessMessage
    ){
        return chessMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChessMessage addUser(
            @Payload ChessMessage chessMessage,
            SimpMessageHeaderAccessor headerAccessor
    ){
        headerAccessor.getSessionAttributes().put("userid", chessMessage.getSender());
        return chessMessage;
    }
}
