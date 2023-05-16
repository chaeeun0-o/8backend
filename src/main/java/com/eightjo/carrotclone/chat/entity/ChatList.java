package com.eightjo.carrotclone.chat.entity;

import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.chat.dto.ChatRresponseDto;
import com.eightjo.carrotclone.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "chatList")
public class ChatList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    @OneToMany(mappedBy = "chatList", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChatRoom> chatRooms;


    public ChatList(ChatRresponseDto chatRresponseDto, String imgPath) {
        this.title = chatRresponseDto.getTitle();
        this.image = imgPath;
        this.content = chatRresponseDto.getContent();
        this.nickname = chatRresponseDto.getNickname();
        this.chatRooms = new ArrayList<>();
    }
}
