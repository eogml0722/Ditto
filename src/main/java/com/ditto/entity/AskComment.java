package com.ditto.entity;

import com.ditto.dto.BoardWriteDTO;
import com.ditto.dto.CommentWriteDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="askcomment")
@Getter
@Setter
public class AskComment extends BaseEntity {
    @Id
    @Column(name="askcomment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="askboard_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AskBoard askBoard;


    public void updateComment(CommentWriteDTO commentWriteDTO){
        this.content = commentWriteDTO.getContent();
    }
}
