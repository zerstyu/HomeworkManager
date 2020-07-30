package com.manager.homework.repository;

import com.manager.homework.domain.Assignment;
import com.manager.homework.vo.AssignmentDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QAssignment.assignment;

@Repository
public class AssignmentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AssignmentRepositorySupport(JPAQueryFactory queryFactory) {
        super(Assignment.class);
        this.queryFactory = queryFactory;
    }

    public List<Assignment> findByCondition(AssignmentDto assignmentDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (assignmentDto.getUserId() != null) {
            builder.and(assignment.user.id.eq(assignmentDto.getUserId()));
        }

        if (assignmentDto.getSubjectId() != null) {
            builder.and(assignment.subject.id.eq(assignmentDto.getSubjectId()));
        }

        if (assignmentDto.getNoticeId() != null) {
            builder.and(assignment.notice.id.eq(assignmentDto.getNoticeId()));
        }

        return queryFactory.selectFrom(assignment)
                .where(builder)
                .fetch();
    }
}
