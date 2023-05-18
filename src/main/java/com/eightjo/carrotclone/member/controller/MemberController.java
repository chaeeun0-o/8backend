package com.eightjo.carrotclone.member.controller;

import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import com.eightjo.carrotclone.member.dto.LoginRequestDto;
import com.eightjo.carrotclone.member.dto.LoginResponseDto;
import com.eightjo.carrotclone.member.dto.SingupRequestDto;
import com.eightjo.carrotclone.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<DefaultRes<String>> signup(@RequestBody @Valid SingupRequestDto signupRequestDto ) {
        memberService.signup(signupRequestDto);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.CREATED_USER));
    }

    @GetMapping("/checkId")
    public ResponseEntity<Object> checkId(@RequestParam("id") String id) {
        memberService.checkId(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultDataRes<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        LoginResponseDto loginResponseDto = memberService.login(loginRequestDto, response);
        return ResponseEntity.ok(new DefaultDataRes<LoginResponseDto>(ResponseMessage.LOGIN_SUCCESS, loginResponseDto));
    }

    @GetMapping("/logout")
    public ResponseEntity<DefaultRes<String>> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
        memberService.logout(userDetails.getMember(), request);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.LOGOUT_SUCCESS));
    }

    @GetMapping("/newAccess")
    public ResponseEntity<Object> newAccessToken(
            @CookieValue(value = "Refresh_Token", required = false) String refreshToken,
            HttpServletRequest request,
            HttpServletResponse response){
        memberService.callNewAccessToken(refreshToken, request, response);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/address")
    public ResponseEntity<DefaultRes<String>> getAddress(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.getAddress(userDetails.getMember());
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.LOGOUT_SUCCESS));
    }
}
