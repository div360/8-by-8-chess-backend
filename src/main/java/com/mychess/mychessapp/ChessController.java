package com.mychess.mychessapp;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ChessController {
    private final ChessMessageService chessMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chess")
    public void sendChessMessage(@Payload ChessMessage chessMessage) {
        var savedChessMessage = chessMessageService.saveChessMessage(chessMessage);
        simpMessagingTemplate.convertAndSendToUser(chessMessage.getReceiverId(),"/queue/messages",savedChessMessage);
    }
}
