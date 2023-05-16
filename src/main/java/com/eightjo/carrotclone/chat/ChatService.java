package com.eightjo.carrotclone.chat;


import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.chat.dto.ChatDto;
import com.eightjo.carrotclone.chat.dto.ChatRresponseDto;
import com.eightjo.carrotclone.chat.entity.ChatList;
import com.eightjo.carrotclone.chat.entity.ChatRoom;
import com.eightjo.carrotclone.chat.repository.ChatRepository;
import com.eightjo.carrotclone.global.dto.PageDto;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.global.validator.BoardValidator;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.member.entity.Member;
import com.eightjo.carrotclone.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ChatService {
    private Map<String, ChatRoom> chatRooms;
    private final BoardValidator boardValidator;
    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

        headerAccessor.getSessionAttributes().put("nickname", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());

        chatDto.setMessage(chatDto.getSender() + "님이 당근에 참여하셨습니다.");
        return chatDto;
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickname");

        ChatDto chatDto = ChatDto.builder()
                .type(ChatDto.MessageType.LEAVE)
                .roomId(roomId)
                .sender(nickName)
                .message(nickName + "님이 당근을 나가셨습니다.")
                .build();

        return chatDto;
    }


    @Transactional(readOnly = true)
    public PageDto getAllChatByNickname(Pageable pageable) {
        Page<ChatList> chatLists = chatRepository.findAll(pageable);
        List<ChatList> responseList = chatLists.getContent();
        List<ChatRresponseDto> chatRresponseDtoList = new ArrayList<>();
        return new PageDto(chatRresponseDtoList, chatLists.getTotalPages());
    }

}
