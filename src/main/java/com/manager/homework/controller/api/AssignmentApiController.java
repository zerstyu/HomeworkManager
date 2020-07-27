package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.Assignment;
import com.manager.homework.service.AssignmentService;
import com.manager.homework.vo.AssignmentDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"4. Assignment"})
@AllArgsConstructor
public class AssignmentApiController {
    private AssignmentService assignmentService;

    @ApiOperation(value = "과제 리스트 조회", notes = "과제 리스트 조회")
    @GetMapping("/assignments")
    public Response<List<Assignment>> getAssignmentList(AssignmentDto assignmentDto) {
        return Response.ok(assignmentService.getAssignmentList(assignmentDto));
    }

    @ApiOperation(value = "과제 생성", notes = "과제 생성")
    @PostMapping("/assignments")
    public Response<Assignment> createAssignment(@RequestBody AssignmentDto assignmentDto) {
        return Response.ok(assignmentService.createAssignment(assignmentDto));
    }

    @ApiOperation(value = "과제 상세 조회", notes = "과제 상세 조회")
    @GetMapping("/assignments/{id}")
    public Response<Assignment> getAssignment(@PathVariable("id") Long id) {
        return Response.ok(assignmentService.getAssignment(id));
    }

    @ApiOperation(value = "과제 정보 수정", notes = "과제 정보 수정")
    @PutMapping("/assignments/{id}")
    public Response<Assignment> updateAssignment(@PathVariable("id") Long id, @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentService.updateAssignment(id, assignmentDto);
        return Response.ok(assignment);
    }

    @ApiOperation(value = "과제 삭제", notes = "과제 삭제")
    @DeleteMapping("/assignments/{id}")
    public Response<String> deleteAssignment(@PathVariable("id") Long id) {
        assignmentService.deleteAssignment(id);
        return Response.ok();
    }
}
