package com.manager.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentResponse {
    private String feedback;
    private String grade;
    private Boolean isOpen;
    private String d_day;
    private List<AssignmentFileResponse> assignmentFileList;
}