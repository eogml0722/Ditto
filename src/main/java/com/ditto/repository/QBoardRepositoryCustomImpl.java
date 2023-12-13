package com.ditto.repository;

import com.ditto.constant.QNAStatus;
import com.ditto.dto.QBoardSearchDTO;
import com.ditto.entity.QItem;
import com.ditto.entity.QaQBoard;
import com.ditto.entity.aQBoard;
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

public class QBoardRepositoryCustomImpl implements QBoardRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public QBoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(QNAStatus searchSellStatus) {
        return searchSellStatus == null ? null : QaQBoard.aQBoard.qnaStatus.eq(searchSellStatus);
    }
    private BooleanExpression regDtsAfter (String searchDateType) {
        // 현재 시간 설정
        LocalDateTime dateTime = LocalDateTime.now();

        if (StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
            // 1일 1주 1달 6달
        } else if(StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusDays(6);
        }
        return QaQBoard.aQBoard.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("title", searchBy)){
            return QaQBoard.aQBoard.title.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)){
            return QaQBoard.aQBoard.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<aQBoard> getQBoardList(QBoardSearchDTO qBoardSearchDTO, Pageable pageable) {
        List<aQBoard> content = queryFactory.selectFrom(QaQBoard.aQBoard)

                .where(regDtsAfter(qBoardSearchDTO.getSearchDateType()),
                        searchSellStatusEq(qBoardSearchDTO.getSearchQNAStatus()),
                        searchByLike(qBoardSearchDTO.getSearchBy(), qBoardSearchDTO.getSearchQuery())
                )

                .orderBy(QaQBoard.aQBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(Wildcard.count)
                .from(QaQBoard.aQBoard)
                .where(regDtsAfter(qBoardSearchDTO.getSearchDateType()),
                        searchSellStatusEq(qBoardSearchDTO.getSearchQNAStatus()),
                        searchByLike(qBoardSearchDTO.getSearchBy(), qBoardSearchDTO.getSearchQuery())
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

}
