package com.eightjo.carrotclone.like.repository;

import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like, Long>{
    Optional<Like> findByMemberIdAndBoardId(Long member_id, Long board_id);

    List<Like> findAllByMemberId(Long id);
}

