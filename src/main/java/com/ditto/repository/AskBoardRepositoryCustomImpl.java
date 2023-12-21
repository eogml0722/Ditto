package com.ditto.repository;

import com.ditto.constant.AskStatus;
import com.ditto.dto.AskBoardSearchDTO;
import com.ditto.entity.AskBoard;
import com.ditto.entity.QAskBoard;
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

public class AskBoardRepositoryCustomImpl implements AskBoardRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public AskBoardRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(AskStatus searchSellStatus) {
        return searchSellStatus == null ? null : QAskBoard.askBoard.askStatus.eq(searchSellStatus);
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
        return QAskBoard.askBoard.regTime.after(dateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery){
        if(StringUtils.equals("title", searchBy)){
            return QAskBoard.askBoard.title.like("%"+searchQuery+"%");
        } else if (StringUtils.equals("createdBy", searchBy)){
            return QAskBoard.askBoard.createdBy.like("%"+searchQuery+"%");
        }
        return null;
    }

    @Override
    public Page<AskBoard> getAskBoardList(AskBoardSearchDTO askBoardSearchDTO, Pageable pageable) {
        List<AskBoard> content = queryFactory.selectFrom(QAskBoard.askBoard)

                .where(regDtsAfter(askBoardSearchDTO.getSearchDateType()),
                        searchSellStatusEq(askBoardSearchDTO.getSearchAskStatus()),
                        searchByLike(askBoardSearchDTO.getSearchBy(), askBoardSearchDTO.getSearchQuery())
                )

                .orderBy(QAskBoard.askBoard.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(Wildcard.count)
                .from(QAskBoard.askBoard)
                .where(regDtsAfter(askBoardSearchDTO.getSearchDateType()),
                        searchSellStatusEq(askBoardSearchDTO.getSearchAskStatus()),
                        searchByLike(askBoardSearchDTO.getSearchBy(), askBoardSearchDTO.getSearchQuery())
                ).fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
