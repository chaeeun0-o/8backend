package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class MapResponseDto {
    @NotBlank
    @JsonProperty("region_1depth_name")
    private String region1depthName;
    @NotBlank
    @JsonProperty("region_2depth_name")
    private String region2depthName;
    @NotBlank
    @JsonProperty("region_3depth_name")
    private String region3depthName;

    public MapResponseDto(String region1depthName, String region2depthName, String region3depthName) {
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
    }
}
