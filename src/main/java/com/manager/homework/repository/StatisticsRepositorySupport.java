package com.manager.homework.repository;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Assignment;
import com.manager.homework.vo.StatisticsDto;
import com.manager.homework.vo.StatisticsResultDto;
import com.manager.homework.vo.StatisticsSubjectTotalScoreDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.manager.homework.domain.QAssignment.assignment;
import static com.manager.homework.domain.QSubject.subject;
import static com.manager.homework.domain.QUser.user;

@Repository
public class StatisticsRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public StatisticsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Assignment.class);
        this.queryFactory = queryFactory;
    }

    public List<StatisticsSubjectTotalScoreDto> findByCondition(Long subjectId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (subjectId != null) {
            builder.and(assignment.subject.id.eq(subjectId));
        }

        List<StatisticsResultDto> resultDtoList =
                queryFactory
                        .select(Projections.fields(StatisticsResultDto.class,
                                user.id.as("userId"),
                                user.name.as("userName"),
                                subject.id.as("subjectId"),
                                subject.name.as("subjectName"),
                                assignment.score.sum().as("totalScore")))
                        .from(assignment)
                        .join(user).on(user.id.eq(assignment.user.id))
                        .join(subject).on(subject.id.eq(assignment.subject.id))
                        .where(builder)
                        .groupBy(user.id, user.name, subject.id, subject.name)
                        .orderBy(assignment.score.sum().desc())
                        .fetch();

        List<StatisticsSubjectTotalScoreDto> dtoList = Lists.newArrayList();
        addStatisticsSubjectTotalScoreDto(dtoList, resultDtoList);
        return dtoList;
    }

    private double getAverage(List<StatisticsResultDto> resultDtoList) {
        return resultDtoList.stream()
                .mapToLong(StatisticsResultDto::getTotalScore)
                .average()
                .orElse(0);
    }

    private void addStatisticsSubjectTotalScoreDto(
            List<StatisticsSubjectTotalScoreDto> dtoList, List<StatisticsResultDto> resultDtoList) {
        dtoList.add(
                StatisticsSubjectTotalScoreDto.builder()
                        .subjectId(resultDtoList.get(0).getSubjectId())
                        .subjectName(resultDtoList.get(0).getSubjectName())
                        .average(getAverage(resultDtoList))
                        .statisticsDtoList(
                                resultDtoList.stream()
                                        .map(v -> StatisticsDto.builder()
                                                .userId(v.getUserId())
                                                .userName(v.getUserName())
                                                .totalScore(v.getTotalScore())
                                                .build())
                                        .collect(Collectors.toList()))
                        .build());
    }
}
