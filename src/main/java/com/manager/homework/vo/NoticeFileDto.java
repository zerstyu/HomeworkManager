package com.manager.homework.vo;

import lombok.Data;

@Data
public class NoticeFileDto {
    private Long userId;
    private Long noticeId;
    private String fileString;
}