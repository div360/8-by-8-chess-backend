package com.mychess.mychessapp.chessRoom;

import com.mychess.mychessapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChessRoomService {
    private final ChessRoomRepository chessRoomRepository;
    private final UserRepository userRepository;

    public Optional<String> findChessRoomId(String senderId, String receiverId) {
        return chessRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
    }

    public String createChessRoom(String senderId) {

        String chessRoomId;
        String receiverId;
        do {
            chessRoomId = UUID.randomUUID().toString();
        } while(chessRoomRepository.findById(chessRoomId).isPresent());

        do {
            receiverId = UUID.randomUUID().toString();
        } while(userRepository.findById(receiverId).isPresent());

        ChessRoom senderRecipient = ChessRoom.builder()
                .roomId(chessRoomId)
                .senderId(senderId)
                .receiverId(receiverId)
                .build();

        ChessRoom recipientSender = ChessRoom.builder()
                .roomId(chessRoomId)
                .senderId(receiverId)
                .receiverId(senderId)
                .build();
        chessRoomRepository.save(senderRecipient);
        chessRoomRepository.save(recipientSender);
        return chessRoomId;
    }
}
