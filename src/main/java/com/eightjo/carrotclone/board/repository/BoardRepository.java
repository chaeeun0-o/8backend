package com.eightjo.carrotclone.board.repository;

import com.eightjo.carrotclone.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository <Board,Long> {
}
