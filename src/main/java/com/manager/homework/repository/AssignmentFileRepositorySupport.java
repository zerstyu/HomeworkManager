package com.manager.homework.repository;

import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.vo.AssignmentFileDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QAssignmentFile.assignmentFile;

@Repository
public class AssignmentFileRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AssignmentFileRepositorySupport(JPAQueryFactory queryFactory) {
        super(AssignmentFile.class);
        this.queryFactory = queryFactory;
    }

    public List<AssignmentFile> findByCondition(AssignmentFileDto assignmentFileDto) {
        BooleanBuilder builder = new BooleanBuilder();
        if (assignmentFileDto.getType() != null) {
            builder.and(assignmentFile.type.eq(assignmentFileDto.getType()));
        }

        if (assignmentFileDto.getUserId() != null) {
            builder.and(assignmentFile.user.id.eq(assignmentFileDto.getUserId()));
        }

        if (assignmentFileDto.getAssignmentId() != null) {
            builder.and(assignmentFile.assignment.id.eq(assignmentFileDto.getAssignmentId()));
        }

        return queryFactory.selectFrom(assignmentFile)
                .where(builder)
                .fetch();
    }
}
