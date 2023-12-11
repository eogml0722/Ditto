package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="boardimage")
@Getter
@Setter
public class BoardImage extends BaseEntity{
    @Id
    @Column(name="boardimage_id")
    @GeneratedValue
    private Long id;
    private String oname;
    private String sname;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY) // 하나의 보드는 여러개의 사진을...
    @JoinColumn(name="qboard_id")
    private QBoard qBoard;

    public void updateBoardImg(String oname, String sname, String url){
        this.oname = oname;
        this.sname = sname;
        this.url = url;
    }

}
