package com.eightjo.carrotclone.board.controller;

import com.eightjo.carrotclone.board.dto.BoardRequestDto;
import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.board.dto.BoardUpdateRequestDto;
import com.eightjo.carrotclone.board.service.BoardService;
import com.eightjo.carrotclone.global.dto.PageDto;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class BoardController {

    private final BoardService boardService;

    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createBoard(@ModelAttribute BoardRequestDto boardRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return boardService.createBoard(boardRequestDto, userDetails);
    }

    @GetMapping("/myBoard")
    public ResponseEntity<?> getMyPosts( @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardResponseDtoList = boardService.getMyPost(userDetails);
        return ResponseEntity.ok(new DefaultDataRes<>(ResponseMessage.BOARD_GET, boardResponseDtoList));
    }

    @GetMapping("/board")
    public ResponseEntity<?> getPosts(Pageable pageable, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        PageDto pageDto = boardService.getAllPost(pageable, userDetails);
        return ResponseEntity.ok(new DefaultDataRes<>(ResponseMessage.BOARD_GET, pageDto));
    }


    @PutMapping(value = "/board/{boardId}")
    public ResponseEntity<?> updateBoard(@PathVariable Long boardId, @RequestBody BoardUpdateRequestDto boardRequestDto, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(boardId, boardRequestDto, userDetails);
    }

    @DeleteMapping("/board/{boardId}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(boardId, userDetails);
    }


    @PutMapping("/board/sell/{boardId}")
    public ResponseEntity<?> changePost(@PathVariable Long boardId, @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardService.changePost(boardId, userDetails);
        return ResponseEntity.ok(null);
    }
}
