package com.eightjo.carrotclone.board.entity;


import com.eightjo.carrotclone.board.dto.BoardRequestDto;
import com.eightjo.carrotclone.global.entity.TimeStamped;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.map.Address;
import com.eightjo.carrotclone.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, String imgPath) {
        this.title = boardRequestDto.getTitle();
        this.image = imgPath;
        this.content = boardRequestDto.getContent();
        this.price = boardRequestDto.getPrice();
        this.likes = new ArrayList<>();
    }
    public void setMember(Member member) {
        this.member = member;
        this.address = member.getAddress();
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.price = boardRequestDto.getPrice();
    }
}
