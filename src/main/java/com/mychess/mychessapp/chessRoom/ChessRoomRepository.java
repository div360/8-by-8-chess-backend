package com.mychess.mychessapp.chessRoom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChessRoomRepository extends MongoRepository<ChessRoom, String> {
    Optional<ChessRoom> findByRoomId(String roomId);
}
