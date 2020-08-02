package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.JoinSubjectService;
import com.manager.homework.vo.RequestJoinSubjectDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/joinSubjects")
@Api(tags = {"7. JoinSubject"})
@RequiredArgsConstructor
public class JoinSubjectApiController {
    private final JoinSubjectService joinSubjectService;

    @ApiOperation(value = "과목 참여", notes = "특정 과목 참여")
    @PostMapping
    public Response<String> createJoinSubject(@RequestBody RequestJoinSubjectDto dto) {
        joinSubjectService.createJoinSubject(dto);
        return Response.ok();
    }

    @ApiOperation(value = "참여한 과목 삭제", notes = "참여한 특정 과목 삭제")
    @DeleteMapping("/{id}")
    public Response<String> deleteJoinSubject(@PathVariable("id") Long joinSubjectId) {
        joinSubjectService.deleteJoinSubject(joinSubjectId);
        return Response.ok();
    }
}