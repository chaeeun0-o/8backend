package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Meta {
    @JsonProperty("total_count")
    private int totalCount;

    @Builder
    public Meta(int totalCount) {
        this.totalCount = totalCount;
    }
}