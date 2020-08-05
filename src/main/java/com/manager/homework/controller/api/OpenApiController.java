package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.OpenApiService;
import com.manager.homework.vo.OpenApiAssignment;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/open_api")
@Api(tags = {"11. Open API"})
@AllArgsConstructor
public class OpenApiController {
    private OpenApiService openApiService;

    @ApiOperation(value = "과제 리스트 조회", notes = "Open API 과제 리스트")
    @GetMapping("/assignments")
    public Response<List<OpenApiAssignment>> getAssignmentList() {
        return Response.ok(openApiService.getAssignmentList());
    }
}