//package com.ditto.entity;
//
//
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//import org.modelmapper.ModelMapper;
//import org.springframework.web.accept.MappingMediaTypeFileExtensionResolver;
//
//import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter @Setter @ToString
//public class Board extends BaseEntity {
//    @Id
//    @Column(name = "board_id")
//    @GeneratedValue
//    private Long id;
//
//    //멤버는 BaseEntity로 대체
//
//    @Column(nullable = false)
//    private String title;
//
//    private String content;
//
//    //조회수
//    @Column(nullable = false)
//    private int viewCount;
//
//    //게시판 분류
//    private BoardCategory boardCategory;
//
//    //사용자 정보
//    private Member member;
//
//
//    @OneToMany(mappedBy = "board")
//    //이미지 업로드 시 사용
//    private List<Img> imgList = new ArrayList<>();
//
//
//
//
//    //static 아니면 생성자로 엔티티인스턴스를 생성해서 메서드를 써야한다.
//
//
//    public static Board createBoard(Long id, String title, String content,
//                 BoardCategory boardCategory, Member member, List<Img> imgList) {
//        Board board = new Board();
//
//        board.setId(id);
//        board.setTitle(title);
//        board.setContent(content);
//        board.setBoardCategory(boardCategory);
//        board.setMember(member);
//        board.setImgList(imgList);
//
//        return board;
//    }
//}
