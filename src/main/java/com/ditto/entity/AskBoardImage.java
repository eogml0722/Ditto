package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="askboardimage")
@Getter
@Setter
public class AskBoardImage extends BaseEntity{
    @Id
    @Column(name="askboardimage_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String oname;
    private String sname;
    private String url;
    @ManyToOne
    @JoinColumn(name="askboard_id")
    private AskBoard askBoard;

    public void updateBoardImg(String oname, String sname, String url){
        this.oname = oname;
        this.sname = sname;
        this.url = url;
    }

}
