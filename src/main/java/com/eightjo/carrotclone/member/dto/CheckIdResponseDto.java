package com.eightjo.carrotclone.member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CheckIdResponseDto {
    @Size(min = 4, max = 10, message ="아이디의 길이는 4자 이상, 10자 이하이어야 합니다. ")
    @Pattern(regexp = "[a-z0-9]*$", message = "아이디는 소문자와 숫자로만 구성되어야 합니다. ")
    private String userId;
}
