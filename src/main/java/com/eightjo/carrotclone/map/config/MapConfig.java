package com.eightjo.carrotclone.map.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MapConfig {

    @Value("${KAKAO-API-KEY}")
    private String apiKey;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String MAP_URL_F = "https://dapi.kakao.com/v2/local/geo/coord2address.json?";
    public static final String MAP_URL_L = "&input_coord=WGS84";

    public static final String KEY_NAME = "Authorization";

    public static final String KEY_PREFIX = "KakaoAK ";


    public String getApiKey() {
        return apiKey;
    }
}
