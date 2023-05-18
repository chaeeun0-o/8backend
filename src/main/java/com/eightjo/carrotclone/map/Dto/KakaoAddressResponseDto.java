package com.eightjo.carrotclone.map.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KakaoAddressResponseDto {
    @JsonProperty("documents")
    private List<AddressDocuments> documents;
    @JsonProperty("meta")
    private AddressMeta meta;

    @Builder
    public KakaoAddressResponseDto(List<AddressDocuments> documents, AddressMeta meta) {
        this.documents = documents;
        this.meta = meta;
    }

    @Data
    @NoArgsConstructor
    public static class AddressDocuments{

        @NotNull
        @JsonProperty("x")
        private double x;
        @NotNull
        @JsonProperty("y")
        private double y;

        public AddressDocuments(double x, double y) {
            this.x = x;
            this.y = y;
        }

    }

    @Data
    @NoArgsConstructor
    public static class AddressMeta{
        @JsonProperty("is_end")
        private boolean isEnd;
        @JsonProperty("pageable_count")
        private int pageableCount;
        @JsonProperty("total_count")
        private int totalCount;

        @Builder
        public AddressMeta(boolean isEnd, int pageableCount, int totalCount) {
            this.isEnd = isEnd;
            this.pageableCount = pageableCount;
            this.totalCount = totalCount;
        }
    }
}
