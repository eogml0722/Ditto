package com.ditto.entity;

import com.ditto.constant.QNAStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="qboard")
@Getter
@Setter
public class aQBoard extends BaseEntity {
    @Id
    @Column(name="qboard_id")
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private QNAStatus qnaStatus;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="boardimage_id")
    private List<BoardImage> boardImage;

}
