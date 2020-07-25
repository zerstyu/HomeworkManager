package com.manager.homework.vo;

import lombok.Data;

@Data
public class AssignmentDto {
    private Long userId;
    private Long subjectId;
    private String title;
    private String content;
}