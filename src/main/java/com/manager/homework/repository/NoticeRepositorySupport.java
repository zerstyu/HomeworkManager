package com.manager.homework.repository;

import com.manager.homework.domain.Notice;
import com.manager.homework.vo.NoticeDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QNotice.notice;

@Repository
public class NoticeRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public NoticeRepositorySupport(JPAQueryFactory queryFactory) {
        super(Notice.class);
        this.queryFactory = queryFactory;
    }

    public List<Notice> findByCondition(NoticeDto noticeDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (noticeDto.getUserId() != null) {
            builder.and(notice.user.id.eq(noticeDto.getUserId()));
        }

        if (noticeDto.getSubjectId() != null) {
            builder.and(notice.subject.id.eq(noticeDto.getSubjectId()));
        }

        if (noticeDto.getTitle() != null) {
            builder.and(notice.title.contains(noticeDto.getTitle()));
        }

        if (noticeDto.getType() != null) {
            builder.and(notice.type.eq(noticeDto.getType()));
        }

        if (noticeDto.getStatus() != null) {
            builder.and(notice.status.eq(noticeDto.getStatus()));
        }

        return queryFactory.selectFrom(notice)
                .where(builder)
                .fetch();
    }
}