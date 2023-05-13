package com.eightjo.carrotclone.board.dto;

import com.eightjo.carrotclone.board.entity.Board;
import lombok.Data;

@Data
public class BoardResponseDto {
    private Long id;

    private String title;

    private String image;

    private String content;

    private String address;

    private boolean likeStatus;

    private int likeCount;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.image = board.getImage();
        this.content = board.getContent();
        this.address= board.getAddress();
        this.likeStatus = false;
        this.likeCount = board.getLikes().size();
    }

    public void setLikeStatus(boolean status) {
        this.likeStatus = status;
    }
}
