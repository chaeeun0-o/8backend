package com.eightjo.carrotclone.board.entity;


import com.eightjo.carrotclone.board.dto.BoardRequestDto;
import com.eightjo.carrotclone.board.dto.BoardUpdateRequestDto;
import com.eightjo.carrotclone.global.entity.TimeStamped;
import com.eightjo.carrotclone.like.entity.Like;
import com.eightjo.carrotclone.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "board", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    public Board(BoardRequestDto boardRequestDto, String imgPath) {
        this.title = boardRequestDto.getTitle();
        this.image = imgPath;
        this.content = boardRequestDto.getContent();
        this.price = boardRequestDto.getPrice();
        this.address = boardRequestDto.getAddress();
        this.likes = new ArrayList<>();
    }
    public void setMember(Member member) {
        this.member = member;
    }

    public void update(BoardUpdateRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.content = boardRequestDto.getContent();
        this.price = boardRequestDto.getPrice();
        this.address = boardRequestDto.getAddress();
    }
}
