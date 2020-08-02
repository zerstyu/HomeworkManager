package com.manager.homework.vo;

import com.manager.homework.type.NoticeStatus;
import com.manager.homework.type.NoticeType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class NoticeResponse {
    private String title;
    private String content;
    private String d_day;
    private NoticeType type;
    private NoticeStatus status;
    private LocalDate expiredAt;
    private Long userId;
    private String userName;
    private String subjectName;
    private List<NoticeFileResponse> noticeFileResponseList;
}