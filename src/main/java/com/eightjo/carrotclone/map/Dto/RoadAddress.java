package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoadAddress {
    @JsonProperty("zone_no")
    private String zoneNo;
    @JsonProperty("building_name")
    private String buildingName;
    @JsonProperty("sub_building_no")
    private String subBuildingNo;
    @JsonProperty("main_building_no")
    private String mainBuildingNo;
    @JsonProperty("underground_yn")
    private String undergroundYn;
    @JsonProperty("road_name")
    private String roadName;
    @JsonProperty("region_3depth_name")
    private String region3depthName;
    @JsonProperty("region_2depth_name")
    private String region2depthName;
    @JsonProperty("region_1depth_name")
    private String region1depthName;
    @JsonProperty("address_name")
    private String addressName;

    @Builder
    public RoadAddress(String zoneNo, String buildingName, String subBuildingNo, String mainBuildingNo, String undergroundYn, String roadName, String region3depthName, String region2depthName, String region1depthName, String addressName) {
        this.zoneNo = zoneNo;
        this.buildingName = buildingName;
        this.subBuildingNo = subBuildingNo;
        this.mainBuildingNo = mainBuildingNo;
        this.undergroundYn = undergroundYn;
        this.roadName = roadName;
        this.region3depthName = region3depthName;
        this.region2depthName = region2depthName;
        this.region1depthName = region1depthName;
        this.addressName = addressName;
    }
}
