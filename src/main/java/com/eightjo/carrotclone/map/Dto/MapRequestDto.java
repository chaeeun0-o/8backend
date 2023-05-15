package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
public class MapRequestDto {
    @NotBlank
    @JsonProperty("region_1depth_name")
    private String region1depthName;
    @NotBlank
    @JsonProperty("region_2depth_name")
    private String region2depthName;
    @NotBlank
    @JsonProperty("region_3depth_name")
    private String region3depthName;
}
