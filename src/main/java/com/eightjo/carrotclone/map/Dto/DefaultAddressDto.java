package com.eightjo.carrotclone.map.Dto;

import com.eightjo.carrotclone.map.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DefaultAddressDto {
    private String region1depthName;
    private String region2depthName;
    private String region3depthName;

    @Builder
    public DefaultAddressDto(Address address) {
        this.region1depthName = address.getRegion1depthName();
        this.region2depthName = address.getRegion2depthName();
        this.region3depthName = address.getRegion3depthName();
    }
}
