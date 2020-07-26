package com.manager.homework.repository;

import com.manager.homework.domain.NoticeFile;
import com.manager.homework.vo.NoticeFileDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QNoticeFile.noticeFile;

@Repository
public class NoticeFileRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeFileRepositorySupport(JPAQueryFactory queryFactory) {
        super(NoticeFile.class);
        this.queryFactory = queryFactory;
    }

    public List<NoticeFile> findByCondition(NoticeFileDto noticeFileDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (noticeFileDto.getUserId() != null) {
            builder.and(noticeFile.user.id.eq(noticeFileDto.getUserId()));
        }

        if (noticeFileDto.getNoticeId() != null) {
            builder.and(noticeFile.notice.id.eq(noticeFileDto.getNoticeId()));
        }

        return queryFactory.selectFrom(noticeFile)
                .where(builder)
                .fetch();
    }
}