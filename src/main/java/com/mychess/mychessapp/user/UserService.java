package com.mychess.mychessapp.user;

import com.mychess.mychessapp.chessRoom.ChessRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ChessRoomRepository chessRoomRepository;

    public void saveUser(User user) {
        String randomId;
        do {
            randomId = UUID.randomUUID().toString();
        } while(userRepository.findById(randomId).isPresent());

        user.setId(randomId);
        user.setOnline(true);
        userRepository.save(user);
    }

    public void disconnectUser(User user) {
        User storedUser = userRepository.findById(user.getId()).orElseThrow();
        if(storedUser.isOnline() == true) {
            storedUser.setOnline(false);
            userRepository.save(storedUser);
        }
    }

    public List<User> findConnectedUsers() {
        return userRepository.findAllByIsOnline(true);
    }

    public String findRecieverIdFromRoomIdAndSenderId(String roomId, String senderId) {
        return chessRoomRepository.findByRoomIdAndSenderId(roomId, senderId).orElseThrow().getReceiverId();
    }
}
