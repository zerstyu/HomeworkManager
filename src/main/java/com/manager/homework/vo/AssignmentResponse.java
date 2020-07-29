package com.manager.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentResponse {
    private Long assignmentId;
    private String feedback;
    private int score;
    private Boolean isOpen;
    private String note;
    private List<AssignmentFileResponse> assignmentFileList;
}