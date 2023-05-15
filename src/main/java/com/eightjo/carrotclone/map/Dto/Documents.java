package com.eightjo.carrotclone.map.Dto;

import ch.qos.logback.core.model.Model;
import com.eightjo.carrotclone.chat.ChatService;
import com.eightjo.carrotclone.chat.entity.ChatRoom;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Documents {
    @JsonProperty("address")
    private AddressDto address;
    @JsonProperty("road_address")
    private RoadAddress roadAddress;

    public Documents(AddressDto address, RoadAddress roadAddress) {
        this.address = address;
        this.roadAddress = roadAddress;
    }

    @Controller
    @RequiredArgsConstructor
    @RequestMapping("/chat")
    public static class ChatRoomController {
        private final ChatService chatService;

        // 채팅 리스트 화면
        @GetMapping("/room")
        public String rooms(Model model) {
            return "/chat/room";
        }


        // 모든 채팅방 목록 반환
        @GetMapping("/rooms")
        @ResponseBody
        public List<ChatRoom> room() {
            return chatService.findAllRoom();
        }


        // 채팅방 생성
        @PostMapping("/room")
        @ResponseBody
        public ChatRoom createRoom(@RequestParam String name) {
            return chatService.createRoom(name);
        }


    //    // 채팅방 입장 화면
    //    @GetMapping("/room/enter/{roomId}")
    //    public String roomDetail(Model model, @PathVariable String roomId) {
    //        model.addAttribute("roomId", roomId);
    //        return "/chat/roomdetail";
    //    }


        // 특정 채팅방 조회
        @GetMapping("/room/{roomId}")
        @ResponseBody
        public ChatRoom roomInfo(@PathVariable String roomId) {
            return chatService.findById(roomId);
        }
    }
}
