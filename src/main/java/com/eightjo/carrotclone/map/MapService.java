package com.eightjo.carrotclone.map;


import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.map.Dto.Documents;
import com.eightjo.carrotclone.map.Dto.KakaoMapRequestDto;
import com.eightjo.carrotclone.map.Dto.KakaoMapResponseDto;
import com.eightjo.carrotclone.map.Dto.MapResponseDto;
import com.eightjo.carrotclone.map.config.MapConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapConfig mapConfig;
    private static RestTemplate restTemplate = new RestTemplate();

    public MapResponseDto getAddress(KakaoMapRequestDto kakaoMapRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(MapConfig.KEY_NAME, MapConfig.KEY_PREFIX + mapConfig.getApiKey());

        ResponseEntity<KakaoMapResponseDto> responseEntity = restTemplate.exchange(
                MapConfig.MAP_URL_F + "x=" + kakaoMapRequestDto.getX() + "&y=" + kakaoMapRequestDto.getY()+MapConfig.MAP_URL_L,
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                KakaoMapResponseDto.class);

        KakaoMapResponseDto kakaoMapResponseDto = responseEntity.getBody();
        if(kakaoMapResponseDto == null){
            throw new CustomException(ResponseMessage.KAKAO_GET_ADDRESS_FAIL,StatusCode.METHOD_NOT_ALLOWED);
        }
        if(kakaoMapResponseDto.getDocuments().isEmpty()){
            throw new CustomException(ResponseMessage.KAKAO_GET_ADDRESS_FAIL,StatusCode.METHOD_NOT_ALLOWED);
        }
        Documents documents = kakaoMapResponseDto.getDocuments().get(0);
        MapResponseDto mapResponseDto = new MapResponseDto(
                documents.getAddress().getRegion1depthName(),
                documents.getAddress().getRegion2depthName(),
                documents.getAddress().getRegion3depthName());

        return mapResponseDto;
    }
}
