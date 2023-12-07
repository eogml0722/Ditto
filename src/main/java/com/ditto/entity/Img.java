package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class Img extends BaseEntity {
    @Id
    @Column(name = "img_id")
    @GeneratedValue
    private Long id;

    private String url;

    private String originName;

    private String savedName;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


}
