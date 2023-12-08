package com.ditto.entity;


import com.ditto.constant.BoardCategory;
import com.ditto.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;

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
    @Column(nullable = false)
    private String viewCount;

    //게시판 분류
    private BoardCategory boardCategory;


    @OneToMany(mappedBy = "board")
    //이미지 업로드 시 사용
    private List<Img> imgList = new ArrayList<>();




    //static 아니면 생성자로 엔티티인스턴스를 생성해서 메서드를 써야한다.



}
