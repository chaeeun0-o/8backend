package com.eightjo.carrotclone.map;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Kakao Map Api", description = "Kakao Map 연결 API")
@RestController
@RequestMapping("/api/map")
@RequiredArgsConstructor
public class MapController {
}
