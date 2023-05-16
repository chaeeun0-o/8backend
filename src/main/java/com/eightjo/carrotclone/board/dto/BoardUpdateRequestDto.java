package com.eightjo.carrotclone.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BoardUpdateRequestDto {
    private String title;
    private String content;
    private String price;
}