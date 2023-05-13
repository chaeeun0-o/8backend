package com.eightjo.carrotclone.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PageDto {
    private List<?> responseDtos;
    private Integer totalPage;

    public PageDto(List<?> responseDtos, Integer totalPage) {
        this.responseDtos = responseDtos;
        this.totalPage = totalPage;
    }
}
