package com.manager.homework.repository;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Assignment;
import com.manager.homework.vo.StatisticsAvgByAssignmentDto;
import com.manager.homework.vo.StatisticsDto;
import com.manager.homework.vo.StatisticsSubjectTotalScoreDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.manager.homework.domain.QAssignment.assignment;
import static com.manager.homework.domain.QNotice.notice;
import static com.manager.homework.domain.QSubject.subject;
import static com.manager.homework.domain.QUser.user;

@Repository
public class StatisticsRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public StatisticsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Assignment.class);
        this.queryFactory = queryFactory;
    }

    public List<StatisticsSubjectTotalScoreDto> findByTotalScore(Long subjectId) {
        List<StatisticsDto> resultList =
                queryFactory
                        .select(Projections.fields(StatisticsDto.class,
                                user.id.as("userId"),
                                user.name.as("userName"),
                                subject.id.as("subjectId"),
                                subject.name.as("subjectName"),
                                assignment.score.sum().as("totalScore")))
                        .from(assignment)
                        .join(user).on(user.id.eq(assignment.user.id))
                        .join(subject).on(subject.id.eq(assignment.subject.id))
                        .where(getBooleanBuilderBySubjectId(subjectId).and(assignment.feedback.isNotNull()))
                        .groupBy(user.id, user.name, subject.id, subject.name)
                        .orderBy(assignment.score.sum().desc())
                        .fetch();

        return getStatisticsSubjectTotalScoreDto(resultList);
    }

    private List<StatisticsSubjectTotalScoreDto> getStatisticsSubjectTotalScoreDto(
            List<StatisticsDto> resultList) {

        List<StatisticsSubjectTotalScoreDto> dtoList = Lists.newArrayList();

        resultList.stream()
                .collect(Collectors.groupingBy(StatisticsDto::getSubjectId))
                .forEach((key, value) -> dtoList.add(
                        StatisticsSubjectTotalScoreDto.builder()
                                .subjectId(value.get(0).getSubjectId())
                                .subjectName(value.get(0).getSubjectName())
                                .average(getAverage(value))
                                .statisticsDtoList(getTotalScoreStatisticsDtoList(value))
                                .build()));

        return dtoList;
    }

    public List<StatisticsAvgByAssignmentDto> findByAvg(Long subjectId) {
        List<StatisticsDto> resultList =
                queryFactory
                        .select(Projections.fields(StatisticsDto.class,
                                subject.id.as("subjectId"),
                                subject.name.as("subjectName"),
                                notice.title.as("title"),
                                assignment.id.as("assignmentId"),
                                assignment.score.avg().as("averageScore")))
                        .from(assignment)
                        .join(subject).on(subject.id.eq(assignment.subject.id))
                        .join(notice).on(notice.id.eq(assignment.notice.id))
                        .where(getBooleanBuilderBySubjectId(subjectId).and(assignment.feedback.isNotNull()))
                        .groupBy(subject.id, subject.name, assignment.id, notice.title)
                        .orderBy(assignment.score.avg().desc())
                        .fetch();

        return getStatisticsAvgByAssignmentDtoDto(resultList);
    }

    private List<StatisticsAvgByAssignmentDto> getStatisticsAvgByAssignmentDtoDto(
            List<StatisticsDto> resultList) {

        List<StatisticsAvgByAssignmentDto> dtoList = Lists.newArrayList();

        resultList.stream()
                .collect(Collectors.groupingBy(StatisticsDto::getSubjectId))
                .forEach((key, value) -> dtoList.add(
                        StatisticsAvgByAssignmentDto.builder()
                                .subjectId(value.get(0).getSubjectId())
                                .subjectName(value.get(0).getSubjectName())
                                .statisticsDtoList(getAvgByStatisticsDtoList(value))
                                .build()));

        return dtoList;
    }

    private BooleanBuilder getBooleanBuilderBySubjectId(Long subjectId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (subjectId != null) {
            builder.and(assignment.subject.id.eq(subjectId));
        }
        return builder;
    }

    private double getAverage(List<StatisticsDto> resultList) {
        return resultList.stream()
                .mapToLong(StatisticsDto::getTotalScore)
                .average()
                .orElse(0);
    }

    private List<StatisticsDto> getTotalScoreStatisticsDtoList(List<StatisticsDto> value) {
        return value.stream()
                .map(v -> StatisticsDto.builder()
                        .userId(v.getUserId())
                        .userName(v.getUserName())
                        .totalScore(v.getTotalScore())
                        .build())
                .collect(Collectors.toList());
    }

    private List<StatisticsDto> getAvgByStatisticsDtoList(List<StatisticsDto> value) {
        return value.stream()
                .map(v -> StatisticsDto.builder()
                        .assignmentId(v.getAssignmentId())
                        .title(v.getTitle())
                        .averageScore(v.getAverageScore())
                        .build())
                .collect(Collectors.toList());
    }
}