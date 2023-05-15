package com.eightjo.carrotclone.chat;


import com.eightjo.carrotclone.chat.dto.ChatDto;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;



@Service
public class ChatService {
    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        // 예외처리
        //반환 결과를 socket session에 사용자의 id로 저장
        headerAccessor.getSessionAttributes().put("nickname", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());

        chatDto.setMessage(chatDto.getSender() + "님이 채팅에 참여하셨습니다.");
        return chatDto;
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickname");

//        chatRoomRepository.deleteByRoomId(roomId);

        ChatDto chatDto = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .roomId(roomId)
                .sender(nickName)
                .message(nickName + "님이 채팅을 나가셨습니다.")
                .build();

        return chatDto;
    }

}
