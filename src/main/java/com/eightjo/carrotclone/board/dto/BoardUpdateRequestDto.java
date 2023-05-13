package com.eightjo.carrotclone.board.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BoardUpdateRequestDto {
        private String title;
        private MultipartFile image;
        private String content;
        private Long price;
        private String address;
        private Boolean like;
        private Boolean status;
    }
