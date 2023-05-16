package com.eightjo.carrotclone.board.service;

import com.eightjo.carrotclone.board.Util.S3Uploader;
import com.eightjo.carrotclone.board.dto.BoardRequestDto;
import com.eightjo.carrotclone.board.dto.BoardResponseDto;
import com.eightjo.carrotclone.board.dto.BoardUpdateRequestDto;
import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.board.repository.BoardRepository;
import com.eightjo.carrotclone.global.dto.PageDto;
import com.eightjo.carrotclone.global.dto.http.DefaultDataRes;
import com.eightjo.carrotclone.global.dto.http.DefaultRes;
import com.eightjo.carrotclone.global.dto.http.ResponseMessage;
import com.eightjo.carrotclone.global.dto.http.StatusCode;
import com.eightjo.carrotclone.global.exception.CustomException;
import com.eightjo.carrotclone.global.security.UserDetailsImpl;
import com.eightjo.carrotclone.global.validator.BoardValidator;
import com.eightjo.carrotclone.like.repository.LikeRepository;
import com.eightjo.carrotclone.member.entity.Member;
import com.eightjo.carrotclone.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardValidator boardValidator;
    private final S3Uploader s3Uploader;

    //게시글 입력
    @Transactional
    public ResponseEntity<?> createBoard(BoardRequestDto boardRequestDto, UserDetailsImpl userDetailsImp) {
        if (boardRequestDto.getTitle() == null || boardRequestDto.getImage() == null || boardRequestDto.getPrice() == null) {
            throw new CustomException(ResponseMessage.WRONG_FORMAT, StatusCode.BAD_REQUEST);
        }
        try {
            Member member = getMember(userDetailsImp.getMember().getUserId());
            String imgPath = s3Uploader.upload(boardRequestDto.getImage());
            Board board = new Board(boardRequestDto, imgPath);
            board.setMember(member);
            board.setAddress(member.getAddress());

            boardRepository.save(board);
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);

            return ResponseEntity.ok(new DefaultDataRes<BoardResponseDto>(ResponseMessage.BOARD_CREATE, boardResponseDto));
        } catch (IOException e) {
            throw new CustomException(ResponseMessage.S3_ERROR, StatusCode.INTERNAL_SERVER_ERROR);
        }
    }
    //게시글 수정
    @Transactional
    public ResponseEntity<?> updateBoard(Long boardId, BoardUpdateRequestDto boardRequestDto, UserDetailsImpl userDetails) {
        Board board = findBoardOrElseThrow(boardId, ResponseMessage.BOARD_UPDATE_FAIL);

        if (!board.getMember().getUserId().equals(userDetails.getMember().getUserId())) {
            throw new CustomException(ResponseMessage.BOARD_UPDATE_FAIL, StatusCode.BAD_REQUEST);
        }
        System.out.println(boardRequestDto.toString());
        board.update(boardRequestDto);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return ResponseEntity.ok(new DefaultRes<BoardResponseDto>(ResponseMessage.BOARD_UPDATE));
    }
    //게시글 삭제
    @Transactional
    public ResponseEntity<?> deleteBoard(Long boardId, UserDetailsImpl userDetails) {
        Board board = findBoardOrElseThrow(boardId, ResponseMessage.BOARD_DELETE_FAIL);

        if (!board.getMember().getUserId().equals(userDetails.getMember().getUserId())) {
            throw new CustomException(ResponseMessage.BOARD_DELETE_FAIL, StatusCode.BAD_REQUEST);
        }

        String imgPath = board.getImage();
        if (s3Uploader.delete(imgPath)) { // S3 에서 이미지 파일 삭제가 성공하면 DB에 있는 게시글도 삭제
            boardRepository.delete(board);
            return ResponseEntity.ok(new DefaultRes<>(ResponseMessage.BOARD_DELETE));
        }

        throw new CustomException(ResponseMessage.BOARD_DELETE_FAIL, StatusCode.INTERNAL_SERVER_ERROR);
    }

    @Transactional(readOnly = true)
    public PageDto getAllPost(Pageable pageable, UserDetailsImpl userDetails) {
        Member member = getMember(userDetails.getMember().getUserId());
        Page<Board> posts = boardRepository.findAllByAddressId(pageable, member.getAddress().getId());
        List<Board> responseList = posts.getContent();
        List<BoardResponseDto> postResponseDtoList = new ArrayList<>();

        for (Board board : responseList) {
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            if (likeRepository.findByMemberIdAndBoardId(member.getId(), board.getId()).isPresent()) {
                boardResponseDto.setLikeStatus(true);
            }
            postResponseDtoList.add(boardResponseDto);
        }

        return new PageDto(postResponseDtoList, posts.getTotalPages());
    }

    @Transactional
    public BoardResponseDto getPost(Long boardId, UserDetailsImpl userDetails) {
        Member member = getMember(userDetails.getMember().getUserId());
        Board board = boardValidator.validateExistPost(boardId);
        BoardResponseDto responseDto = new BoardResponseDto(board);

        likeRepository.findByMemberIdAndBoardId(member.getId(), board.getId()).ifPresent(like -> {
            responseDto.setLikeStatus(true);
        });

        return responseDto;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getMyPost(UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardList = boardRepository.findAllByMemberId((userDetails.getMember().getId())).stream().map(BoardResponseDto::new).toList();
        if (boardList.size() == 0) {
            return null;
        }
        return boardList;
    }

    @Transactional
    public void changePost(Long boardId, UserDetailsImpl userDetails) {
        Board board = findBoardOrElseThrow(boardId, ResponseMessage.BOARD_UPDATE_FAIL);

        if (!board.getMember().getUserId().equals(userDetails.getMember().getUserId())) {
            throw new CustomException(ResponseMessage.BOARD_UPDATE_FAIL, StatusCode.BAD_REQUEST);
        }

        board.setStatus(true);
    }

    @Transactional(readOnly = true)
    public Board findBoardOrElseThrow(Long boardId, String msg) {
        return boardRepository.findById(boardId).orElseThrow(
                () -> new CustomException(msg, StatusCode.BAD_REQUEST)
        );
    }


    private Member getMember(String userId) {
        return memberRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ResponseMessage.NOT_FOUND_USER, StatusCode.BAD_REQUEST)
        );
    }
}
