package com.manager.homework.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.manager.homework.domain.Assignment;
import com.manager.homework.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.manager.homework.domain.QAssignment.assignment;
import static com.manager.homework.domain.QNotice.notice;
import static com.manager.homework.domain.QSubject.subject;
import static com.manager.homework.domain.QUser.user;

@Slf4j
@Repository
public class StatisticsRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public StatisticsRepositorySupport(JPAQueryFactory queryFactory) {
        super(Assignment.class);
        this.queryFactory = queryFactory;
    }

    public List<StatisticsSubjectTotalAvgDto> findByTotalAvg(Long subjectId) {
        List<StatisticsDto> resultList =
                queryFactory
                        .select(Projections.fields(StatisticsDto.class,
                                user.id.as("userId"),
                                user.name.as("userName"),
                                subject.id.as("subjectId"),
                                subject.name.as("subjectName"),
                                assignment.score.avg().as("averageScore")))
                        .from(assignment)
                        .join(user).on(user.id.eq(assignment.user.id))
                        .join(subject).on(subject.id.eq(assignment.subject.id))
                        .where(getBooleanBuilderBySubjectId(subjectId).and(assignment.feedback.isNotNull()))
                        .groupBy(user.id, user.name, subject.id, subject.name)
                        .orderBy(assignment.score.avg().desc())
                        .fetch();

        return getStatisticsSubjectTotalAvgDto(resultList);
    }

    private List<StatisticsSubjectTotalAvgDto> getStatisticsSubjectTotalAvgDto(List<StatisticsDto> resultList) {
        List<StatisticsSubjectTotalAvgDto> dtoList = Lists.newArrayList();

        resultList.stream()
                .collect(Collectors.groupingBy(StatisticsDto::getSubjectId))
                .forEach((key, value) -> dtoList.add(
                        StatisticsSubjectTotalAvgDto.builder()
                                .subjectId(value.get(0).getSubjectId())
                                .subjectName(value.get(0).getSubjectName())
                                .statisticsDtoList(getTotalAvgStatisticsDtoList(value))
                                .build()));

        return dtoList;
    }

    public List<StatisticsAvgByAssignmentDto> findByAvg(Long subjectId) {
        List<StatisticsDto> resultList =
                queryFactory
                        .select(Projections.fields(StatisticsDto.class,
                                subject.id.as("subjectId"),
                                subject.name.as("subjectName"),
                                notice.id.as("noticeId"),
                                notice.title.as("title"),
                                assignment.score.avg().as("averageScore")))
                        .from(assignment)
                        .join(subject).on(subject.id.eq(assignment.subject.id))
                        .join(notice).on(notice.id.eq(assignment.notice.id))
                        .where(getBooleanBuilderBySubjectId(subjectId).and(assignment.feedback.isNotNull()))
                        .groupBy(subject.id, subject.name, notice.id, notice.title)
                        .orderBy(assignment.score.avg().desc())
                        .fetch();

        return getAvgByAssignmentDtoList(resultList);
    }

    private List<StatisticsAvgByAssignmentDto> getAvgByAssignmentDtoList(
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

//    private double getAverage(List<StatisticsDto> resultList) {
//        return resultList.stream()
//                .mapToLong(StatisticsDto::getTotalScore)
//                .average()
//                .orElse(0);
//    }

    private List<StatisticsDto> getTotalAvgStatisticsDtoList(List<StatisticsDto> value) {
        return value.stream()
                .map(v -> StatisticsDto.builder()
                        .userId(v.getUserId())
                        .userName(v.getUserName())
                        .averageScore(v.getAverageScore())
                        .build())
                .collect(Collectors.toList());
    }

    private List<StatisticsDto> getAvgByStatisticsDtoList(List<StatisticsDto> value) {
        return value.stream()
                .map(it -> StatisticsDto.builder()
                        .noticeId(it.getNoticeId())
                        .title(it.getTitle())
                        .averageScore(it.getAverageScore())
                        .build())
                .collect(Collectors.toList());
    }

    public List<StatisticsCategoryAvgDto> findByCategoryAvg(Long userId) {
        log.info("cdy userId = {}", userId);

        List<StatisticsDto> resultList = queryFactory
                .select(Projections.fields(StatisticsDto.class,
                        user.id.as("userId"),
                        user.name.as("userName"),
                        subject.categoryGroup,
                        subject.category,
                        assignment.score.avg().as("averageScore")))
                .from(assignment)
                .join(user).on(user.id.eq(assignment.user.id))
                .join(subject).on(subject.id.eq(assignment.subject.id))
                .where(getBooleanBuilderByUserId(userId).and(assignment.feedback.isNotNull()))
                .groupBy(user.id, user.name, subject.categoryGroup, subject.category)
                .orderBy(assignment.score.avg().desc(), subject.categoryGroup.desc(), subject.category.desc())
                .fetch();

        return getCategoryAvgDtoList(resultList);
    }

    private List<StatisticsCategoryAvgDto> getCategoryAvgDtoList(List<StatisticsDto> resultList) {
        List<StatisticsCategoryAvgDto> dtoList = Lists.newArrayList();

        resultList.stream()
                .collect(Collectors.groupingBy(StatisticsDto::getUserId))
                .forEach((key, value) -> dtoList.add(
                        StatisticsCategoryAvgDto.builder()
                                .userId(value.get(0).getUserId())
                                .userName(value.get(0).getUserName())
                                .categoryList(value.stream().map(it -> it.getCategoryGroup().getCategoryGroup()
                                        + ">" + it.getCategory().getCategory()).collect(Collectors.toList()))
                                .averageList(value.stream().map(StatisticsDto::getAverageScore).collect(Collectors.toList()))
                                .build()));
        return dtoList;
    }

    private BooleanBuilder getBooleanBuilderByUserId(Long userId) {
        BooleanBuilder builder = new BooleanBuilder();
        if (userId != null) {
            builder.and(user.id.eq(userId));
        }
        return builder;
    }

    public StatisticsSubjectRangeAvgDto findByRangeAvg(Long subjectId, Long userId) {
        List<StatisticsSubjectTotalAvgDto> list = findByTotalAvg(subjectId);
        log.info("size = " + list.size());

        StatisticsSubjectTotalAvgDto dto = list.get(0);

        log.info("StatisticsSubjectTotalAvgDto = " + dto);
        StatisticsDto statisticsDto =
                dto.getStatisticsDtoList().stream()
                        .filter(it -> it.getUserId().equals(userId))
                        .findFirst()
                        .orElse(new StatisticsDto());

        log.info("statisticsDto = " + statisticsDto);

        return StatisticsSubjectRangeAvgDto.builder()
                .subjectId(statisticsDto.getSubjectId())
                .subjectName(statisticsDto.getSubjectName())
                .userId(statisticsDto.getUserId())
                .userName(statisticsDto.getUserName())
                .userRangeIndex(getRangeIndex(statisticsDto.getAverageScore()))
                .rangeList(new ArrayList<>(makeRangeMap().keySet()))
                .countList(getCountList(dto.getStatisticsDtoList(), makeRangeMap()))
                .build();
    }

    public Map<String, Integer> makeRangeMap() {
        Map<String, Integer> map = Maps.newLinkedHashMap();

        for (int i = 0; i < 10; i++) {
            map.put((i * 10) + "-" + (i * 10 + 10), 0);
        }

        return map;
    }

    private List<Integer> getCountList(List<StatisticsDto> statisticsDtoList, Map<String, Integer> map) {
        statisticsDtoList.forEach(it -> {
                    String range = getRange(it.getAverageScore());
                    map.put(range, map.get(range) + 1);
                }
        );

        List<Integer> countList = Lists.newArrayList();
        map.forEach((key, value) -> countList.add(value));

        return countList;
    }

    private String getRange(Double avg) {
        for (int i = 0; i < 10; i++) {
            if (i * 10 <= avg && avg < i * 10 + 10) {
                return (i * 10) + "-" + (i * 10 + 10);
            }
        }
        return "";
    }

    private int getRangeIndex(Double avg) {
        for (int i = 0; i < 10; i++) {
            if (i * 10 <= avg && avg < i * 10 + 10) {
                return i;
            }
        }

        return 0;
    }
}