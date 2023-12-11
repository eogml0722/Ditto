package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="aboard")
@Getter
@Setter
public class ABoard extends BaseEntity{
    @Id
    @Column(name="aboard_id")
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="member_id")
//    private Member member;
//    @OneToOne
//    @JoinColumn(name="qboard_id")
//    private QBoard qBoard;
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name="boardimage_id")
//    private List<BoardImage> boardImage;
}
