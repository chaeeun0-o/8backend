package com.eightjo.carrotclone.like.dto;

import com.eightjo.carrotclone.board.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "좋아요 응답 DTO")
public class LikeResponseDto {
    private boolean likeStatus;
    private int likeCount;

    public LikeResponseDto(Board board, boolean likeStatus) {
        this.likeStatus = likeStatus;
        this.likeCount = board.getLikes().size();
    }
}
