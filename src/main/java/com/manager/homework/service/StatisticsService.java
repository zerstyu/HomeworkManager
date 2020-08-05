package com.manager.homework.service;

import com.manager.homework.repository.StatisticsRepositorySupport;
import com.manager.homework.vo.StatisticsAvgByAssignmentDto;
import com.manager.homework.vo.StatisticsSubjectTotalScoreDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final StatisticsRepositorySupport statisticsRepositorySupport;

    public List<StatisticsSubjectTotalScoreDto> getTotalScoreRank(Long subjectId) {
        return statisticsRepositorySupport.findByTotalScore(subjectId);
    }

    public List<StatisticsAvgByAssignmentDto> getAvgScoreByAssignment(Long subjectId) {
        return statisticsRepositorySupport.findByAvg(subjectId);
    }
}