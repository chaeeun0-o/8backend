package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class KakaoMapResponseDto {

    @JsonProperty("documents")
    private List<Documents> documents;
    @JsonProperty("meta")
    private Meta meta;

    @Builder
    public KakaoMapResponseDto(List<Documents> documents, Meta meta) {
        this.documents = documents;
        this.meta = meta;
    }
}
