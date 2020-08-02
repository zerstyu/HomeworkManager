package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.SubjectService;
import com.manager.homework.vo.RequestSubjectDto;
import com.manager.homework.vo.SearchSubjectDto;
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

    @ApiOperation(value = "과목 리스트 조회", notes = "유저가 만든 과목과 참여한과목 리스트 조회")
    @GetMapping
    public Response<List<SearchSubjectDto>> getSubjectList(Long userId) {
        return Response.ok(subjectService.getSubjectList(userId));
    }

    @ApiOperation(value = "과목 생성", notes = "특정 과목 생성")
    @PostMapping
    public Response<SubjectDto> createSubject(@RequestBody RequestSubjectDto dto) {
        return Response.ok(subjectService.createSubject(dto));
    }

    @ApiOperation(value = "과목 수정", notes = "특정 과목 수정")
    @PutMapping("/{id}")
    public Response<String> updateSubject(@PathVariable("id") Long subjectId, @RequestBody RequestSubjectDto dto) {
        subjectService.updateSubject(subjectId, dto);
        return Response.ok();
    }

    @ApiOperation(value = "과목 삭제", notes = "특정 과목 삭제")
    @DeleteMapping("/{id}")
    public Response<String> deleteSubject(@PathVariable("id") Long subjectId) {
        subjectService.deleteSubject(subjectId);
        return Response.ok();
    }

    @ApiOperation(value = "초대코드로 과목 조회", notes = "초대코드로 과목 조회")
    @GetMapping("/invites")
    public Response<SubjectDto> getJoinSubject(@RequestParam("inviteCode") String inviteCode) {
        return Response.ok(subjectService.getJoinSubject(inviteCode));
    }

}