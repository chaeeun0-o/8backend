package com.eightjo.carrotclone.map;

import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import com.eightjo.carrotclone.map.Dto.DefaultAddressDto;
import com.eightjo.carrotclone.map.Dto.KakaoMapRequestDto;
import com.eightjo.carrotclone.map.Dto.MapRequestDto;
import com.eightjo.carrotclone.map.Dto.MapResponseDto;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/checkAddress")
    public ResponseEntity<Object> checkAddress(
            @RequestParam("region1depthName") String region1depthName,
            @RequestParam("region2depthName") String region2depthName,
            @RequestParam("region3depthName") String region3depthName) {
        return mapService.checkAddress(
                region1depthName,
                region2depthName,
                region3depthName);
    }

    @PostMapping
    public ResponseEntity<DefaultDataRes<List<DefaultAddressDto>>> setAddressList(
            @RequestParam("size") Integer size,
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<DefaultAddressDto> defaultAddressDtoList = mapService.setAddressList(size, userDetails);
        return ResponseEntity.ok(new DefaultDataRes<>(ResponseMessage.KAKAO_GET_ADDRESS_SUCCESS, defaultAddressDtoList));
    }
}
