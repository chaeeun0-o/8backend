package com.eightjo.carrotclone.chat.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatRoom {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String roomId;
//
//    private String roomName;
//
//    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
//    List<Chat> chatLists = new ArrayList<>();
//
//    @Column(name = "host")
//    private String host;
//
//    @Column(name = "guest")
//    private String guest;
//
//    public static ChatRoom of(String host, String guest) {
//        return ChatRoom.builder()
//                .roomId(UUID.randomUUID().toString())
//                .roomName(host + "님이 대화에 참여 했습니다.")
//                .host(host)
//                .guest(guest)
//                .build();
//    }


}
