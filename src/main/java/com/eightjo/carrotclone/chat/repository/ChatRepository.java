package com.eightjo.carrotclone.chat.repository;

import com.eightjo.carrotclone.chat.entity.ChatList;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRepository extends JpaRepository<ChatList, Long> {

}