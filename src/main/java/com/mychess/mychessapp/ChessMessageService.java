package com.mychess.mychessapp;

import com.mychess.mychessapp.chessRoom.ChessRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChessMessageService {
    private final ChessMessageRepository chessMessageRepository;
    private final ChessRoomService chessRoomService;

    ChessMessage saveChessMessage(ChessMessage chessMessage) {
        var chessRoomId = chessRoomService.findChessRoomId(chessMessage.getSenderId(), chessMessage.getReceiverId());
        return chessMessage;
    }
}
