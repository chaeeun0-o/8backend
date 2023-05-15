package com.eightjo.carrotclone.map.Dto;

import ch.qos.logback.core.model.Model;
import com.eightjo.carrotclone.chat.ChatService;
import com.eightjo.carrotclone.chat.entity.ChatRoom;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
