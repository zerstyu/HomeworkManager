package com.manager.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class NoticeDto {
    private Long userId;
    private Long subjectId;
    private String title;
    private String content;
    private List<String> noticeFileStringList;
}