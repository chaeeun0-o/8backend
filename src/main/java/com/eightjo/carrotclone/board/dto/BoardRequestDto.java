package com.eightjo.carrotclone.board.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardRequestDto {
    private String title;

    private MultipartFile image;

    private String content;

    private String price;
}
