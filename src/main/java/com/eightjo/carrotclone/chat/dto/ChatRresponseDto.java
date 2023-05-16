package com.eightjo.carrotclone.chat.dto;

import com.eightjo.carrotclone.chat.entity.ChatList;
import lombok.Data;

@Data
public class ChatRresponseDto {
    private Long id;

    private String nickname;

    private String title;

    private String image;

    private String content;


    public ChatRresponseDto(ChatList chatList) {
        this.id = chatList.getId();
        this.nickname = chatList.getNickname();
        this.title = chatList.getTitle();
        this.image = chatList.getImage();
        this.content = chatList.getContent();
    }
}
