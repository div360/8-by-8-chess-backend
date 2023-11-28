package com.mychess.mychessapp;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChessMessageRepository extends MongoRepository<ChessMessage, String> {
}
