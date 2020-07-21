package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.SubjectService;
import com.manager.homework.vo.SubjectDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/subjects")
@Api(tags = {"2. Subject"})
@RequiredArgsConstructor
public class SubjectApiController {
    private final SubjectService subjectService;

    @ApiOperation(value = "과목 리스트 조회", notes = "과목 리스트 조회")
    @GetMapping("/{member_id}")
    public Response<List<SubjectDto>> getSubjectList(@PathVariable("member_id") String email) {
        return Response.ok(subjectService.getSubjectList(email));
    }

    @ApiOperation(value = "과목 생성", notes = "과목 생성")
    @PostMapping
    public Response<String> createSubject(@RequestBody SubjectDto subjectDto) throws Exception {
        subjectService.createSubject(subjectDto);
        return Response.ok();
    }

    @ApiOperation(value = "과목 수정", notes = "과목 수정")
    @PutMapping
    public Response<String> updateSubject(@RequestBody SubjectDto subjectDto) throws Exception {
        subjectService.updateSubject(subjectDto);
        return Response.ok();
    }

    @ApiOperation(value = "과목 삭제", notes = "과목 삭제")
    @DeleteMapping
    public Response<String> deleteSubject(@RequestBody SubjectDto subjectDto) throws Exception {
        subjectService.deleteSubject(subjectDto);
        return Response.ok();
    }
}