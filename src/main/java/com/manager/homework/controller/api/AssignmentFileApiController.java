package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.service.AssignmentFileService;
import com.manager.homework.vo.AssignmentFileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"5. AssignmentFile"})
@AllArgsConstructor
public class AssignmentFileApiController {
    private final AssignmentFileService assignmentFileService;

    @ApiOperation(value = "파일 리스트 조회", notes = "파일 리스트 조회")
    @GetMapping("/assignment_files")
    public Response<List<AssignmentFile>> getAssignmentFileList(AssignmentFileDto assignmentFileDto) {
        return Response.ok(assignmentFileService.getAssignmentFileList(assignmentFileDto));
    }

    @ApiOperation(value = "파일 생성", notes = "파일 생성")
    @PostMapping("/assignment_files")
    public Response<List<AssignmentFile>> createAssignmentFile(@RequestBody List<AssignmentFileDto> assignmentFileDtoList) throws Exception {
        return Response.ok(assignmentFileService.createAssignmentFile(assignmentFileDtoList));
    }

    @ApiOperation(value = "파일 상세 정보 조회", notes = "파일 상세 정보 조회")
    @GetMapping("/assignment_files/{id}")
    public Response<AssignmentFile> getAssignmentFile(@PathVariable("id") Long id) {
        return Response.ok(assignmentFileService.getAssignmentFile(id));
    }

    @ApiOperation(value = "파일 정보 수정", notes = "파일 정보 수정")
    @PutMapping("/assignment_files/{id}")
    public Response<AssignmentFile> updateAssignmentFile(@PathVariable("id") Long id, @RequestBody AssignmentFileDto assignmentFileDto) throws Exception {
        AssignmentFile assignmentFile = assignmentFileService.updateAssignmentFile(id, assignmentFileDto);
        return Response.ok(assignmentFile);
    }

    @ApiOperation(value = "파일 삭제", notes = "파일 사용자 삭제")
    @DeleteMapping("/assignment_files/{id}")
    public Response<String> deleteAssignmentFile(@PathVariable("id") Long id) {
        assignmentFileService.deleteAssignmentFile(id);
        return Response.ok();
    }
}
