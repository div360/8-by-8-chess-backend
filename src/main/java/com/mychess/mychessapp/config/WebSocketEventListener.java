package com.mychess.mychessapp.config;

import com.mychess.mychessapp.ChessMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;

    @EventListener
    public void handleWebsocketDisconnectListener(
            SessionDisconnectEvent event
    ){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        Integer userid = (Integer) headerAccessor.getSessionAttributes().get("userid");
        if(userid != null){
            log.info("User disconnected:{}", userid);
            var chessMessage = ChessMessage.builder().sender(userid).build();
            messageTemplate.convertAndSend("/topic/public", chessMessage);
        }
    }
}
