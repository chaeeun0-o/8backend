package com.eightjo.carrotclone.member.dto;

import com.eightjo.carrotclone.map.Address;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponseDto {
    @JsonProperty("address")
    private SignupAddress address;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("userId")
    private String userid;

    public LoginResponseDto(SignupAddress address, String nickname, String userid) {
        this.address = address;
        this.nickname = nickname;
        this.userid = userid;
    }

    @Data
    public static class SignupAddress {
        @JsonProperty("y")
        private double y;
        @JsonProperty("x")
        private double x;
        @JsonProperty("region3depthName")
        private String region3depthname;
        @JsonProperty("region2depthName")
        private String region2depthname;
        @JsonProperty("region1depthName")
        private String region1depthname;

        public SignupAddress(Address address) {
            this.y = address.getY();
            this.x = address.getX();
            this.region3depthname = address.getRegion3depthName();
            this.region2depthname = address.getRegion2depthName();
            this.region1depthname = address.getRegion1depthName();
        }
    }
}
