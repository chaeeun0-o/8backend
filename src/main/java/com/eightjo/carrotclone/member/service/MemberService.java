package com.eightjo.carrotclone.member.service;

import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.entity.RefreshToken;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.global.jwt.JwtUtil;
import com.eightjo.carrotclone.global.repository.RefreshTokenRepository;
import com.eightjo.carrotclone.global.validator.TokenValidator;
import com.eightjo.carrotclone.map.Address;
import com.eightjo.carrotclone.map.Dto.KakaoMapRequestDto;
import com.eightjo.carrotclone.map.Dto.MapRequestDto;
import com.eightjo.carrotclone.map.MapRepository;
import com.eightjo.carrotclone.map.MapService;
import com.eightjo.carrotclone.member.dto.LoginRequestDto;
import com.eightjo.carrotclone.member.dto.LoginResponseDto;
import com.eightjo.carrotclone.member.dto.SingupRequestDto;
import com.eightjo.carrotclone.member.dto.TokenDto;
import com.eightjo.carrotclone.member.entity.Member;
import com.eightjo.carrotclone.member.repository.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MapService mapService;
    private final MapRepository mapRepository;
    private final TokenValidator tokenValidator;
    private final JwtUtil jwtUtil;

    //회원가입
    @Transactional
    public void signup(SingupRequestDto signupRequestDto) {
        String userId = signupRequestDto.getUserId();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();

        //중복 아이디 체크
        Optional<Member> found = memberRepository.findByUserId(userId);
        if (found.isPresent()) {
            throw new CustomException(ResponseMessage.ALREADY_ENROLLED_USER, StatusCode.Conflict);
        }
        //중복 닉네임 체크
        Optional<Member> foundMemberNickname = memberRepository.findByNickname(nickname);
        if (foundMemberNickname.isPresent()) {
            throw new CustomException(ResponseMessage.ALREADY_ENROLLED_NICKNAME, StatusCode.Conflict);
        }

        Optional<Address> optionalAddress = mapRepository.findByRegion1depthNameAndRegion2depthNameAndRegion3depthName(
                signupRequestDto.getAddress().getRegion1depthName(),
                signupRequestDto.getAddress().getRegion2depthName(),
                signupRequestDto.getAddress().getRegion3depthName());

        if (optionalAddress.isEmpty()){
            Address address = new Address(
                    signupRequestDto.getAddress().getRegion1depthName(),
                    signupRequestDto.getAddress().getRegion2depthName(),
                    signupRequestDto.getAddress().getRegion3depthName());
            KakaoMapRequestDto kakaoMapRequestDto = mapService.validAddressXY(new MapRequestDto(address));
            address.setX(kakaoMapRequestDto.getX());
            address.setY(kakaoMapRequestDto.getY());

            Member member = new Member(userId, password, nickname);
            address = mapRepository.save(address);
            member.setAddress(address);
            memberRepository.save(member);
        }
        else {
            Member member = new Member(userId, password, nickname);
            member.setAddress(optionalAddress.get());

            memberRepository.save(member);
        }


    }

    //로그인
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        Member member = memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ResponseMessage.LOGIN_FAIL_ID, StatusCode.UNAUTHORIZED));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ResponseMessage.LOGIN_FAIL_PASSWORD, StatusCode.UNAUTHORIZED);
        }

        //아이디 정보로 토큰 생성
        TokenDto token = jwtUtil.createAllToken(userId);

        //Refresh 토큰 있는지 확인
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(userId);

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(token.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(token.getRefreshToken(), member.getUserId());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response, token);
        new LoginResponseDto(member.getNickname());
    }

    //로그아웃
    @Transactional
    public void logout(Member member, HttpServletRequest request){
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByUserId(member.getUserId());
        String accessToken = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        if(refreshToken.isEmpty()){
            throw new CustomException(ResponseMessage.LOGOUT_FAIL, StatusCode.BAD_REQUEST);
        }
        refreshTokenRepository.delete(refreshToken.get());
    }


    public void getAddress(Member member) {
        memberRepository.findByUserId(member.getUserId()).orElseThrow(
                () -> new CustomException(ResponseMessage.NOT_FOUND_USER, StatusCode.BAD_REQUEST)
        );


    }

    // 헤더 셋팅
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    @Transactional
    public void callNewAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        tokenValidator.tokenNullCheck(token);

        boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
        if (!isRefreshToken) {
            throw new CustomException(ResponseMessage.EXPIRED_TOKEN, StatusCode.UNAUTHORIZED);
        }

        String userId = jwtUtil.getUserInfoFromToken(refreshToken, jwtUtil.getRefreshKey());
        String newAccessToken = jwtUtil.createToken(userId, jwtUtil.getAccessKey(), JwtUtil.ACCESS_TOKEN);
        jwtUtil.setHeaderAccessToken(response, newAccessToken);
    }
}
