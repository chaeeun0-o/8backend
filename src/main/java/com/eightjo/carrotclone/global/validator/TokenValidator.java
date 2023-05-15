package com.eightjo.carrotclone.global.validator;


import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.global.jwt.JwtUtil;
import com.eightjo.carrotclone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

    public void tokenNullCheck(String token) {
        if (token == null) {
            throw new CustomException(ResponseMessage.NOT_FOUND_TOKEN, StatusCode.BAD_REQUEST);
        }
    }
//
//    public void tokenValidateCheck(String token) {
//        if (!jwtUtil.validateToken(token)) {
//            throw new IllegalArgumentException(ExceptionMessage.EXPIRED_TOKEN.getMessage());
//        }
//    }
//
//    public Member findMemberByToken(String token) {
//        String userInfo = jwtUtil.getUserInfoFromToken(token);
//        return memberRepository.findByEmail(userInfo).orElseThrow(
//                () -> new IllegalArgumentException(ExceptionMessage.EXPIRED_TOKEN.getMessage())
//        );
//    }
}
