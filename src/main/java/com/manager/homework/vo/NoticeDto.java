package com.manager.homework.vo;

import com.manager.homework.type.NoticeStatus;
import com.manager.homework.type.NoticeType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NoticeDto {
    private NoticeType type;
    private NoticeStatus status;
    private Long userId;
    private Long subjectId;
    private String title;
    private String content;
    private LocalDate expiredAt;
    private List<String> noticeFileStringList;
}