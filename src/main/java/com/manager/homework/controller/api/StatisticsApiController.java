package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.StatisticsService;
import com.manager.homework.vo.StatisticsAvgByAssignmentDto;
import com.manager.homework.vo.StatisticsCategoryAvgDto;
import com.manager.homework.vo.StatisticsSubjectTotalScoreDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@Api(tags = {"9. statistics"})
@AllArgsConstructor
public class StatisticsApiController {
    private StatisticsService statisticsService;

    @ApiOperation(value = "과목 총합점수 순위 조회", notes = "특정 과목 총합점수 순위 조회")
    @GetMapping("/subjects/total-scores")
    public Response<List<StatisticsSubjectTotalScoreDto>> getTotalScoreRank(
            @RequestParam(value = "id", required = false) Long subjectId) {
        return Response.ok(statisticsService.getTotalScoreRank(subjectId));
    }

    @ApiOperation(value = "과제별 평균 순위 조회", notes = "특정 과목의 과제별 평균 순위 조회")
    @GetMapping("/subjects/averages")
    public Response<List<StatisticsAvgByAssignmentDto>> getAvgScoreByAssignment(
            @RequestParam(value = "id", required = false) Long subjectId) {
        return Response.ok(statisticsService.getAvgScoreByAssignment(subjectId));
    }

    @ApiOperation(value = "유저의 카테고리별 평균 순위 조회", notes = "특정 유저의 카테고리별 평균 순위 조회")
    @GetMapping("/subjects/category_averages")
    public Response<List<StatisticsCategoryAvgDto>> getCategoryAverage(
            @RequestParam(value = "userId", required = false) Long userId) {
        return Response.ok(statisticsService.getCategoryAverage(userId));
    }
}
