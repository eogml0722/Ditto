package com.ditto.entity;

import com.ditto.constant.AskStatus;
import com.ditto.dto.BoardWriteDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="askboard")
@Getter
@Setter
public class AskBoard extends BaseEntity {
    @Id
    @Column(name="askboard_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private AskStatus askStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    public void updateAskBoard(BoardWriteDTO boardWriteDTO){
        this.title = boardWriteDTO.getTitle();
        this.content = boardWriteDTO.getContent();
    }
}
