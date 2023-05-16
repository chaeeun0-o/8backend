package com.eightjo.carrotclone.map;


import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.map.Dto.*;
import com.eightjo.carrotclone.map.config.MapConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MapConfig mapConfig;
    private final MapRepository mapRepository;
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

        return new MapResponseDto(
                documents.getAddress().getRegion1depthName(),
                documents.getAddress().getRegion2depthName(),
                documents.getAddress().getRegion3depthName());
    }

//    public List<Long> getAddressList(Address address,int size) {
//        if ( size < 0 || 3 < size ){
//            throw new CustomException(ResponseMessage.WRONG_FORMAT, HttpStatus.BAD_REQUEST.value());
//        }
//
//        List<Long> addressIdList = new ArrayList<>();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(MapConfig.KEY_NAME, MapConfig.KEY_PREFIX + mapConfig.getApiKey());
//
//        ResponseEntity<KakaoRegionResponseDto> responseEntity = null;
//
//        for (int i = 0; i < size; i++) {
//            responseEntity = restTemplate.exchange(
//                    MapConfig.REGION_URL + "x=" + address.getX() + "&y=" + address.getY(),
//                    HttpMethod.GET,
//                    new HttpEntity<>(null, headers),
//                    KakaoRegionResponseDto.class);
//
//            KakaoRegionResponseDto regionResponseDto = responseEntity.getBody();
//            KakaoRegionResponseDto.RegionDocuments documents = regionResponseDto.getDocuments().get(1);
//
//            Optional<Address> findAddress =  mapRepository.findByXY(documents.getX(), documents.getY());
//
//            if (findAddress.isPresent()){
//
//            }
//
//            Address newAddress = new Address(
//                    documents.getRegion1depthName(),
//                    documents.getRegion2depthName(),
//                    documents.getRegion3depthName(),
//                    documents.getX(),
//                    documents.getY());
//
//        }
//        return addressList;
//    }

    public KakaoMapRequestDto validAddressXY(MapRequestDto mapRequestDto) {
        KakaoAddressResponseDto kakaoAddressResponseDto = validAddress(mapRequestDto);

        return new KakaoMapRequestDto(
                kakaoAddressResponseDto.getDocuments().get(0).getX(),
                kakaoAddressResponseDto.getDocuments().get(0).getY());
    }

    public void validAddressApi(MapRequestDto mapRequestDto) {
        validAddress(mapRequestDto);
    }

    private KakaoAddressResponseDto validAddress(MapRequestDto mapRequestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(MapConfig.KEY_NAME, MapConfig.KEY_PREFIX + mapConfig.getApiKey());

        ResponseEntity<KakaoAddressResponseDto> responseEntity = restTemplate.exchange(
                MapConfig.ADDRESS_URL+ mapRequestDto.getRegion1depthName()+ mapRequestDto.getRegion2depthName()+ mapRequestDto.getRegion3depthName(),
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                KakaoAddressResponseDto.class);

        KakaoAddressResponseDto kakaoAddressResponseDto = responseEntity.getBody();

        if (kakaoAddressResponseDto.getMeta().getTotalCount() == 0)
            throw new CustomException(ResponseMessage.KAKAO_GET_ADDRESS_FAIL, StatusCode.METHOD_NOT_ALLOWED);
        return kakaoAddressResponseDto;
    }
}
