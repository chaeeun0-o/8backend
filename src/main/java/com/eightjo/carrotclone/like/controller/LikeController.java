package com.eightjo.carrotclone.like.controller;

import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class LikeController {
    private final LikeService likeService;

    @GetMapping("/api/like")
    public ResponseEntity<DefaultDataRes<List<BoardResponseDto>>> boardLike(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return likeService.getLikeBoards(userDetails.getMember());
    }
    @PostMapping("/api/like/{boardId}")
    public ResponseEntity<DefaultRes<String>> updateLike(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long boardId){
        return likeService.updateBoardLike(boardId, userDetails.getMember());
    }
}
