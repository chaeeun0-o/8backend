package com.eightjo.carrotclone.chat.repository;

import com.eightjo.carrotclone.chat.entity.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository {
    Optional<ChatRoom> findByRoomId(String roomId);

    Optional<ChatRoom> findByHostAndGuest(String host, String guest);

    void deleteByRoomId(String roomId);
}
