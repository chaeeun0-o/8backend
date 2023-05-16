package com.eightjo.carrotclone.global.validator;

import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.board.repository.BoardRepository;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardValidator {
    private final BoardRepository boardRepository;

    public void validatePostAuthor(Member member, Board board) {
        if (!member.getNickname().equals(board.getMember().getNickname())) {
            throw new CustomException(ResponseMessage.NOT_FOUND_USER, StatusCode.BAD_REQUEST);
        }
    }

    public Board validateExistPost(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new CustomException(ResponseMessage.BOARD_GET_FAIL_ID, StatusCode.BAD_REQUEST)
        );
    }
}
