package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class KakaoRegionResponseDto {

    @JsonProperty("documents")
    private List<RegionDocuments> documents;
    @JsonProperty("meta")
    private Meta meta;

    @Builder
    public KakaoRegionResponseDto(List<RegionDocuments> documents, Meta meta) {
        this.documents = documents;
        this.meta = meta;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RegionDocuments {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;
        @JsonProperty("code")
        private String code;
        @JsonProperty("region_4depth_name")
        private String region4depthName;
        @JsonProperty("region_3depth_name")
        private String region3depthName;
        @JsonProperty("region_2depth_name")
        private String region2depthName;
        @JsonProperty("region_1depth_name")
        private String region1depthName;
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("region_type")
        private String regionType;

        @Builder
        public RegionDocuments(double y, double x, String code, String region4depthName, String region3depthName, String region2depthName, String region1depthName, String addressName, String regionType) {
            this.y = y;
            this.x = x;
            this.code = code;
            this.region4depthName = region4depthName;
            this.region3depthName = region3depthName;
            this.region2depthName = region2depthName;
            this.region1depthName = region1depthName;
            this.addressName = addressName;
            this.regionType = regionType;
        }
    }
}
