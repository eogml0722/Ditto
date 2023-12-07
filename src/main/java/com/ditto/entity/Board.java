package com.ditto.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class Board extends BaseEntity {
    @Id
    @Column(name = "board_id")
    @GeneratedValue
    private Long id;

    //멤버는 BaseEntity로 대체

    @Column(nullable = false)
    private String title;

    private String content;

    //조회수
    private String viewCount;

    @OneToMany(mappedBy = "board")
    //이미지 업로드시 사용
    private List<Img> imgList = new ArrayList<>();




}
