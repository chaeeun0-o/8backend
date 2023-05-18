package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Documents {
    @JsonProperty("address")
    private AddressDto address;
    @JsonProperty("road_address")
    private RoadAddress roadAddress;

    public Documents(AddressDto address, RoadAddress roadAddress) {
        this.address = address;
        this.roadAddress = roadAddress;
    }
}
