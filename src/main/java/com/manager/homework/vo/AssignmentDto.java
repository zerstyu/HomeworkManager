package com.manager.homework.vo;

import lombok.Data;

@Data
public class AssignmentDto {
    private Long userId;
    private Long subjectId;
    private Long noticeId;
    private String feedback;
    private String grade;
    private Boolean isOpen;
}
