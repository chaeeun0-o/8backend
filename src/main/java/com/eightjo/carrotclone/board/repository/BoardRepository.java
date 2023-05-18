package com.eightjo.carrotclone.board.repository;

import com.eightjo.carrotclone.board.entity.Board;
import com.eightjo.carrotclone.map.Address;
import com.eightjo.carrotclone.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository <Board,Long> {
    Page<Board> findAllByAddressId(Pageable pageable, Long address_id);
    Page<Board> findAllByAddressIdAndMemberIdNotIn(Pageable pageable, Long address_id, List<Long> memberList);

    List<Board> findAllByMemberId(Long member_id);
    Page<Board> findAllByAddressIdInAndMemberIdNotIn(Pageable pageable, List<Long> address_id, List<Long> member_id);

    Page<Board> findAllByAddressIn(Pageable pageable, List<Address> address);
    Page<Board> findAllByAddressInAndMemberNotIn(Pageable pageable, List<Address> nearAddressList, List<Member> memberList);
}
