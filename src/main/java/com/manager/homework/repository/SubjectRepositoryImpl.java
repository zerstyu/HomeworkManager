package com.manager.homework.repository;

import com.google.common.collect.Lists;
import com.manager.homework.vo.SubjectDto;
import com.manager.homework.vo.SubjectResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static com.manager.homework.domain.QJoinSubject.joinSubject;
import static com.manager.homework.domain.QSubject.subject;
import static com.manager.homework.domain.QUser.user;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SubjectRepositoryImpl implements SubjectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SubjectResponse> selectAllSubjectList(Long userId) {
        BooleanBuilder builder = new BooleanBuilder();

        if (userId != null) {
            builder.and(user.id.eq(userId));
        }

        return getSearchSubjectDtoList(
                queryFactory.select(user.id).from(user).where(builder).fetch());
    }

    private List<SubjectResponse> getSearchSubjectDtoList(List<Long> userIdList) {
        List<SubjectResponse> subjectResponseList = Lists.newArrayList();

        userIdList.forEach(it -> {
            subjectResponseList.add(
                    SubjectResponse.builder()
                            .userId(it)
                            .userName(getUserName(it))
                            .subjectDtoList(getSubjectDtoList(it))
                            .joinSubjectDtoList(getJoinSubjectDtoList(it)).build());
        });

        return subjectResponseList;
    }

    private String getUserName(Long userId) {
        return Objects.requireNonNull(
                queryFactory.selectFrom(user)
                        .where(user.id.eq(userId))
                        .fetchOne()).getName();
    }

    private List<SubjectDto> getSubjectDtoList(Long userId) {
        return queryFactory
                .select(Projections.fields(SubjectDto.class,
                        subject.id.as("subjectId"),
                        subject.name.as("subjectName"),
                        subject.inviteCode.as("inviteCode"),
                        subject.categoryGroup,
                        subject.category,
                        subject.classType,
                        user.id.as("userId"),
                        user.name.as("userName")))
                .from(subject)
                .join(user).on(user.id.eq(subject.user.id))
                .where(user.id.eq(userId))
                .fetch();
    }

    private List<SubjectDto> getJoinSubjectDtoList(Long userId) {
        return queryFactory
                .select(Projections.fields(SubjectDto.class,
                        joinSubject.id.as("joinSubjectId"),
                        subject.id.as("subjectId"),
                        subject.name.as("subjectName"),
                        subject.inviteCode.as("inviteCode"),
                        subject.categoryGroup,
                        subject.category,
                        subject.classType,
                        user.id.as("userId"),
                        user.name.as("userName")))
                .from(joinSubject)
                .join(subject).on(subject.id.eq(joinSubject.subject.id))
                .join(user).on(user.id.eq(subject.user.id))
                .where(joinSubject.user.id.eq(userId))
                .fetch();
    }
}