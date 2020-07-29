package com.manager.homework.vo;

import com.manager.homework.type.NoticeStatus;
import com.manager.homework.type.NoticeType;
import lombok.Data;

import java.util.List;

@Data
public class NoticeResponse {
    private String title;
    private String content;
    private String d_day;
    private NoticeType type;
    private NoticeStatus status;
    private List<NoticeFileResponse> noticeFileResponseList;
}