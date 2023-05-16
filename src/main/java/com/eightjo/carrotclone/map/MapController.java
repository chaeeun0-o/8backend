package com.eightjo.carrotclone.map;

import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.map.Dto.KakaoMapRequestDto;
import com.eightjo.carrotclone.map.Dto.MapRequestDto;
import com.eightjo.carrotclone.map.Dto.MapResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Kakao Map Api", description = "Kakao Map 연결 API")
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @PostMapping("/coordinate")
    public ResponseEntity<DefaultDataRes<MapResponseDto>> getAddress(@RequestBody @Valid KakaoMapRequestDto kakaoMapRequestDto) {
        MapResponseDto mapResponseDto = mapService.getAddress(kakaoMapRequestDto);
        return ResponseEntity.ok(new DefaultDataRes<>(ResponseMessage.KAKAO_GET_ADDRESS_SUCCESS, mapResponseDto));
    }

    @PostMapping("/address")
    public ResponseEntity<DefaultRes<String>> saveAddress(@RequestBody @Valid MapRequestDto mapRequestDto) {
        mapService.validAddressApi(mapRequestDto);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.KAKAO_GET_ADDRESS_SUCCESS));
    }
}
