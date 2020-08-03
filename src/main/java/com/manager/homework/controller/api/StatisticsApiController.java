package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.StatisticsService;
import com.manager.homework.vo.StatisticsSubjectTotalScoreDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@Api(tags = {"9. statistics"})
@AllArgsConstructor
public class StatisticsApiController {
    private StatisticsService statisticsService;

    @ApiOperation(value = "과목 총합점수 순위 조회", notes = "특정 과목 총합점수 순위 조회")
    @GetMapping("/subjects/{id}")
    public Response<List<StatisticsSubjectTotalScoreDto>> getTotalScoreRank(@PathVariable("id") Long subjectId) {
        return Response.ok(statisticsService.getTotalScoreRank(subjectId));
    }

//    @ApiOperation(value = "과제 공지 상세 조회", notes = "과제 공지 상세 조회")
//    @GetMapping("/notices/{id}")
//    public Response<NoticeResponse> getNotice(@PathVariable("id") Long id) {
//        return Response.ok(statisticsService.getNotice(id));
//    }

}
