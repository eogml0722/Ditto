package com.ditto.repository;

import com.ditto.constant.ItemCategory;
import com.ditto.constant.ItemSellStatus;
import com.ditto.dto.ItemSearchDTO;
import com.ditto.dto.MainItemDTO;
import com.ditto.dto.QMainItemDTO;
import com.ditto.entity.Item;
import com.ditto.entity.QItem;
import com.ditto.entity.QItemImg;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    //EntityManager를 기반으로 생성되고
    //QueryDsl을 사용하고 JPA쿼리를 실행할수 있도록 함
    private JPAQueryFactory queryFactory;

    //생성자에서 EntityManager를 받아서 JPAQueryFactory를 초기화
    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    //카테고리에 따라 itemCategory가 같은 값인지 비교하는 조건을 생성
    private BooleanExpression searchCategoryEq(ItemCategory searchCategory) {
        return searchCategory == null ? null : QItem.item.itemCategory.eq(searchCategory);
    }
    //반환타입 BooleanExpression: Querydsl에서 조건을 표현하기 위해 사용되는 인터페이스
    //sql문의 where에 해당하는 부분
    //상품 판매 상태가 null이면 null을 반환,
    //null이 아니면 QItem 클래스를 이용하여 itemSellStatus가 같은 값인지 비교하는 조건을 생성
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus) {
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    //현재 날짜와 시간을 설정하여 해당 시간 이후로 등록된 상품만 조회하도록
    //조건을 생성
    private BooleanExpression regDtsAfter(String searchDateType) {
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if (StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if (StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if (StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if (StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }
        return QItem.item.regTime.after(dateTime);
    }

    //검색어가 포함되어 있는 상품 또는 아이디를 조회
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if (StringUtils.equals("itemName", searchBy)) {
            return QItem.item.itemName.like("%" + searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)) {
            return QItem.item.createdBy.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {

        //쿼리를 실행해서 조회된 항목을 가져옴
        List<Item> content = queryFactory
                .selectFrom(QItem.item)
                //등록날짜를 기준, 판매 상태 기준, 검색어를 기준으로 하여 조회
                //where절에 ,를 표시하면 and로 실행됨
                //or조건을 이용하고자 한다면 BooleanBuilder를 사용
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchCategoryEq(itemSearchDTO.getSearchCategory()),
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(),
                                itemSearchDTO.getSearchQuery()))
                .orderBy(QItem.item.id.desc()) //정렬
                .offset(pageable.getOffset())  //데이터를 가져오도록 시작 인덱스를 설정
                .limit(pageable.getPageSize())  //한번에 가져올 페이지의 개수
                .fetch(); //리스트를 반환

        //전체 항목수
        long total = queryFactory.select(Wildcard.count)
                .from(QItem.item)
                .where(regDtsAfter(itemSearchDTO.getSearchDateType()),
                        searchSellStatusEq(itemSearchDTO.getSearchSellStatus()),
                        searchByLike(itemSearchDTO.getSearchBy(),
                                itemSearchDTO.getSearchQuery())
                )
                .fetchOne();

        //조회된 항목의 리스트, 페이지정보, 전체항목수를 page객체로 반환
        return new PageImpl<>(content, pageable, total);
    }
    
    //검색어가 null이 아니면 상품명에 해당 검색어가 포함되는 상품을 조회하는 조건 반환
    private BooleanExpression itemNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDTO> getMainItemPage(ItemSearchDTO itemSearchDTO, Pageable pageable) {
        //Querydsl을 사용하기위해서 Q클래스 선언
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        //QMainItemDto의 생성자에 반환할 값을 입력
        List<MainItemDTO> content = queryFactory.select(
                        new QMainItemDTO(
                                item.id,
                                item.itemName,
                                item.itemDetail,
                                itemImg.imgUrl,
                                item.price)
                )
                .from(itemImg)
                .join(itemImg.item, item) //itemImg와 item을 조인
                .where(itemImg.repImgYn.eq("Y")) //대표이미지만 불러옴
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //전체 아이템의 개수를 조회
        long total = queryFactory.select(Wildcard.count)
                .from(itemImg)
                .join(itemImg.item, item)
                .where(itemImg.repImgYn.eq("Y"))
                .where(itemNameLike(itemSearchDTO.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);

    }

}
