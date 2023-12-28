package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "img")
@Getter @Setter @ToString
public class Img extends BaseEntity {
    @Id
    @Column(name = "img_id")
    @GeneratedValue
    private Long id;

    private String url;

    private String originName;

    private String savedName;


    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void updateImg(String oriImgName, String imgName, String imgUrl) {
        this.originName = oriImgName;
        this.savedName= imgName;
        this.url = imgUrl;
    }
}
