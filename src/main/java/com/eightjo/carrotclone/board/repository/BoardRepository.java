package com.eightjo.carrotclone.board.repository;

import com.eightjo.carrotclone.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository <Board,Long> {
    Page<Board> findAllByAddressId(Pageable pageable, Long address_id);

    List<Board> findAllByMemberId(Long member_id);
}
