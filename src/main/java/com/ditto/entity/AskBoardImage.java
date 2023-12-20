package com.ditto.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="askboard_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AskBoard askBoard;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="askcomment_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AskComment askComment;

    public void updateBoardImg(String oname, String sname, String url){
        this.oname = oname;
        this.sname = sname;
        this.url = url;
    }

}
