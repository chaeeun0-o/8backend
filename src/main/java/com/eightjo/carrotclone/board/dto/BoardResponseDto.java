package com.eightjo.carrotclone.board.dto;

import com.eightjo.carrotclone.board.entity.Board;
import lombok.Data;

@Data
public class BoardResponseDto {
    private Long id;

    private String title;

    private String image;

    private String content;

    private String price;

    private String address;

    private Boolean status;

    private Boolean likeStatus;

    private int likeCount;


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.image = board.getImage();
        this.content = board.getContent();
        this.address= board.getMember().getAddress().getRegion1depthName() + " " +
                board.getMember().getAddress().getRegion2depthName() + " " +
                board.getMember().getAddress().getRegion3depthName();
        this.status = board.isStatus();
        this.likeStatus = false;
        this.likeCount = board.getLikes().size();
        this.price = board.getPrice().toString();
    }

    public void setLikeStatus(boolean status) {
        this.likeStatus = status;
    }
}
