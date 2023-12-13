package com.ditto.entity;

import com.ditto.constant.ASKStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="askboard")
@Getter
@Setter
public class AskBoard extends BaseEntity {
    @Id
    @Column(name="askboard_id")
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private ASKStatus qnaStatus;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="boardimage_id")
    private List<BoardImage> boardImage;

}
