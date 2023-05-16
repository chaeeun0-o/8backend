package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoMapRequestDto {
    @NotNull
    @JsonProperty("x")
    private double x;
    @NotNull
    @JsonProperty("y")
    private double y;

    public KakaoMapRequestDto(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
