package com.eightjo.carrotclone.global.validator;

import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.member.entity.Member;
import com.eightjo.carrotclone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateMemberByUserId(String userId) {
        memberRepository.findByUserId(userId).ifPresent(member -> {
            throw new CustomException(ResponseMessage.NOT_FOUND_USER, StatusCode.BAD_REQUEST);
        });
    }
//
//    public void validateMemberByNickname(String nickname) {
//        memberRepository.findByNickname(nickname).ifPresent(member -> {
//            throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_NICKNAME.getMessage());
//        });
//    }
//
    public Member validateMember(String UserId) {
        Optional<Member> member = memberRepository.findByUserId(UserId);
        if (member.isEmpty()) {
            throw new CustomException(ResponseMessage.NOT_FOUND_USER, HttpStatus.BAD_REQUEST.value());
        } else {
            return member.get();
        }
    }
//
//    public void validatePassword(String password, Member member) {
//        if (!passwordEncoder.matches(password, member.getPassword())) {
//            throw new IllegalArgumentException(ExceptionMessage.NO_MATCH_PASSWORD.getMessage());
//        }
//    }
}
