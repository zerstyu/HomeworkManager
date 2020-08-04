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
    private Long userId;
    private String userName;
    private Long subjectId;
    private String subjectName;
    private Long noticeId;
    private String noticeTitle;
    private List<AssignmentFileResponse> assignmentFileList;
}