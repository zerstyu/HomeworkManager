package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 공지 상태
public enum NoticeStatus {
    PENDING("대기"),
    PROGRESS("진행중"),
    GRADING("채점중"),
    COMPLETED("완료"),
    CANCELED("취소");

    private final String noticeStatus;
}
