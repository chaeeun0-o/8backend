package com.eightjo.carrotclone.member.controller;

import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import com.eightjo.carrotclone.member.dto.LoginRequestDto;
import com.eightjo.carrotclone.member.dto.SingupRequestDto;
import com.eightjo.carrotclone.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import java.lang.reflect.Member;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")

public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SingupRequestDto signupRequestDto ) {
        memberService.signup(signupRequestDto);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.CREATED_USER));
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.LOGIN_SUCCESS));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {
        memberService.logout(userDetails.getMember(), request);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.LOGOUT_SUCCESS));
    }

    @GetMapping("/newAccess")
    public void newAccessToken(
            @CookieValue(value = "Refresh_Token", required = false) String refreshToken,
            HttpServletRequest request,
            HttpServletResponse response){

    }
}
