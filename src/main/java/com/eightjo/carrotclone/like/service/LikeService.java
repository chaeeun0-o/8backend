package com.eightjo.carrotclone.like.service;


import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.validator.BoardValidator;
import com.eightjo.carrotclone.like.dto.LikeResponseDto;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.like.repository.LikeRepository;
import com.eightjo.carrotclone.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final BoardValidator boardValidator;

    /**
     * 좋아요
     */
    @Transactional
    public ResponseEntity<?> updateBoardLike(Long id, Member member) {
        // 게시글 존재확인
        Board board = boardValidator.validateExistPost(id);
        Like boardLike = isPostLike(member, board);

        //좋아요한 사람
        if (boardLike != null) {
            board.getLikes().remove(boardLike);
            return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.BOARD_LIKE_DELETE));
        }

        //좋아요 안 한 사람
        Like like = new Like(member, board);
        return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.BOARD_LIKE_CREATE));
    }

    @Transactional
    public ResponseEntity<DefaultDataRes<?>> getLikeBoards(Member member) {
        List<Board> boardList = likeRepository.findAllByMemberId(member.getId()).stream().map(Like::getBoard).toList();
        List<BoardResponseDto> boardDtoList = boardList.stream().map(BoardResponseDto::new).toList();
        if (boardList.isEmpty()){
            return ResponseEntity.status(StatusCode.NO_CONTENT).body(new DefaultDataRes<List<BoardResponseDto>>(ResponseMessage.BOARD_GET, boardDtoList));
        }
        return ResponseEntity.ok(new DefaultDataRes<List<BoardResponseDto>>(ResponseMessage.BOARD_GET, boardDtoList));
    }

    // 좋아요 여부확인
    private Like isPostLike(Member member, Board board) {
        Optional<Like> like = likeRepository.findByMemberIdAndBoardId(member.getId(), board.getId());
        return like.orElse(null);
    }
}
