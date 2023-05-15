package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {
    @JsonProperty("zip_code")
    private String zipCode;
    @JsonProperty("sub_address_no")
    private String subAddressNo;
    @JsonProperty("main_address_no")
    private String mainAddressNo;
    @JsonProperty("mountain_yn")
    private String mountainYn;
    @JsonProperty("region_3depth_name")
    private String region3depthName;
    @JsonProperty("region_2depth_name")
    private String region2depthName;
    @JsonProperty("region_1depth_name")
    private String region1depthName;
    @JsonProperty("address_name")
    private String addressName;

    @Builder
    public AddressDto(String zipCode, String subAddressNo, String mainAddressNo, String mountainYn, String region3depthName, String region2depthName, String region1depthName, String addressName) {
        this.zipCode = zipCode;
        this.subAddressNo = subAddressNo;
        this.mainAddressNo = mainAddressNo;
        this.mountainYn = mountainYn;
        this.region3depthName = region3depthName;
        this.region2depthName = region2depthName;
        this.region1depthName = region1depthName;
        this.addressName = addressName;
    }
}
