package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.RecommendService;
import com.manager.homework.vo.RecommendResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"9. Recommend"})
@AllArgsConstructor
public class RecommendApiController {
    private RecommendService recommendService;

    @ApiOperation(value = "추천 리스트 조회", notes = "추천 리스트 조회")
    @GetMapping("/recommends")
    public Response<List<RecommendResponse>> getRecommendList(@RequestParam Long userId) {
        return Response.ok(recommendService.getRecommendList(userId));
    }
}