package com.manager.homework.service;

import com.manager.homework.repository.StatisticsRepositorySupport;
import com.manager.homework.vo.StatisticsAvgByAssignmentDto;
import com.manager.homework.vo.StatisticsCategoryAvgDto;
import com.manager.homework.vo.StatisticsSubjectTotalAvgDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepositorySupport statisticsRepositorySupport;

    public List<StatisticsSubjectTotalAvgDto> getTotalAvgRank(Long subjectId) {
        return statisticsRepositorySupport.findByTotalAvg(subjectId);
    }

    public List<StatisticsAvgByAssignmentDto> getAvgScoreByAssignment(Long subjectId) {
        return statisticsRepositorySupport.findByAvg(subjectId);
    }

    public List<StatisticsCategoryAvgDto> getCategoryAverage(Long userId) {
        return statisticsRepositorySupport.findByCategoryAvg(userId);
    }
}