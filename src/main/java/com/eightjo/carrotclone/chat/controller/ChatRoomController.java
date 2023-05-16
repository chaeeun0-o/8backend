package com.eightjo.carrotclone.chat.controller;

import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.chat.ChatService;
import com.eightjo.carrotclone.chat.dto.ChatRresponseDto;
import com.eightjo.carrotclone.global.dto.PageDto;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatService chatService;


    @GetMapping("/chat")
    public ResponseEntity<?> getALlChat(Pageable pageable) {
        PageDto page = chatService.getAllChatByNickname(pageable);
        return ResponseEntity.ok(new DefaultDataRes<>(ResponseMessage.BOARD_GET, page));
    }
}
