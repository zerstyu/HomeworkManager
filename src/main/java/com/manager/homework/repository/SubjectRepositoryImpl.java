package com.manager.homework.repository;

import com.manager.homework.vo.SubjectDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QSubject.subject;
import static com.manager.homework.domain.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SubjectDto> selectSubjectList(Long userId) {
        log.info("selectSubjectList");

        return queryFactory
                .select(Projections.fields(SubjectDto.class,
                        subject.id,
                        subject.name,
                        user.id,
                        user.name
                ))
                .from(subject)
                .join(user).on(subject.user.id.eq(user.id))
                .where(user.id.eq(userId))
                .fetch();
    }
}
