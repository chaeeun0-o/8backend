package com.eightjo.carrotclone.map.Dto;

import com.eightjo.carrotclone.map.Address;
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

    public MapRequestDto(Address address) {
        this.region1depthName = address.getRegion1depthName();
        this.region2depthName = address.getRegion2depthName();
        this.region3depthName = address.getRegion3depthName();
    }

    public MapRequestDto(String region1depthName, String region2depthName, String region3depthName) {
        this.region1depthName = region1depthName;
        this.region2depthName = region2depthName;
        this.region3depthName = region3depthName;
    }
}
