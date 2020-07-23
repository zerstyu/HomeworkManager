package com.manager.homework.repository;

import com.manager.homework.domain.Subject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.manager.homework.domain.QSubject.subject;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Subject> selectAll(Long userId) {
        log.info("querydsl selectAll");

        return queryFactory.selectFrom(subject)
                .where(subject.user.id.eq(userId))
                .fetch();
    }
}
