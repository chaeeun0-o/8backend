package com.eightjo.carrotclone.chat;


import com.eightjo.carrotclone.chat.dto.ChatDto;
import com.eightjo.carrotclone.chat.entity.ChatRoom;
import jakarta.annotation.PostConstruct;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ChatService {
    private Map<String, ChatRoom> chatRooms;
    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("nickname", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());

        chatDto.setMessage(chatDto.getSender() + "님이 채팅에 참여하셨습니다.");
        return chatDto;
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickname");

        ChatDto chatDto = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .roomId(roomId)
                .sender(nickName)
                .message(nickName + "님이 채팅을 나가셨습니다.")
                .build();

        return chatDto;
    }

    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
