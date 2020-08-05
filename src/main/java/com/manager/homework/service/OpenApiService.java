package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Assignment;
import com.manager.homework.repository.AssignmentRepository;
import com.manager.homework.vo.OpenApiAssignment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {
    private final AssignmentRepository assignmentRepository;

    public List<OpenApiAssignment> getAssignmentList() {
        List<OpenApiAssignment> openApiAssignmentList = Lists.newArrayList();
        List<Assignment> assignmentList = assignmentRepository.findByIsOpen(true);
        for(Assignment assignment: assignmentList) {
            OpenApiAssignment openApiAssignment = new OpenApiAssignment();
            openApiAssignment.setSubjectName(assignment.getSubject().getName());
            openApiAssignment.setScore(assignment.getScore());
            openApiAssignment.setLink("/assignments/" + assignment.getId().toString());
            openApiAssignmentList.add(openApiAssignment);
        }
        return openApiAssignmentList;
    }
}
