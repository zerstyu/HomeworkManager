package com.manager.homework.vo;

import lombok.Data;

@Data
public class NoticeDto {
    private Long userId;
    private Long subjectId;
    private String title;
    private String content;
}