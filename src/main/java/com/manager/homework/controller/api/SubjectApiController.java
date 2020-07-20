package com.manager.homework.controller.api;

import com.manager.homework.service.SubjectService;
import com.manager.homework.vo.CommonResponse;
import com.manager.homework.vo.SubjectDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/{email}")
    public ResponseEntity getSubjectList(@PathVariable("email") String email) {
        try {
            List<SubjectDto> subjectDtoList = subjectService.getSubjectList(email);
            return new ResponseEntity(new CommonResponse("SUCCESS", subjectDtoList), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "과목 생성", notes = "과목 생성")
    @PostMapping
    public ResponseEntity createSubject(@RequestBody SubjectDto subjectDto) {
        try {
            subjectService.createSubject(subjectDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", subjectDto), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "과목 수정", notes = "과목 수정")
    @PutMapping
    public ResponseEntity updateSubject(@RequestBody SubjectDto subjectDto) {
        try {
            subjectService.updateSubject(subjectDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", ""), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "과목 삭제", notes = "과목 삭제")
    @DeleteMapping
    public ResponseEntity deleteSubject(@RequestBody SubjectDto subjectDto) {
        try {
            subjectService.deleteSubject(subjectDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", ""), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
