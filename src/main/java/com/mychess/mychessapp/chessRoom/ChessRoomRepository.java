package com.mychess.mychessapp.chessRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChessRoomRepository extends MongoRepository<ChessRoom, String> {
    Optional<String> findBySenderIdAndReceiverId(String senderId, String receiverId);

    Optional<ChessRoom> findByRoomIdAndSenderId(String roomId, String senderId);
}
