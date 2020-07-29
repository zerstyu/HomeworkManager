package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 공지 상태
public enum NoticeStatus {
    PENDING,
    PROGRESS,
    COMPLETED,
    CANCELED
}
