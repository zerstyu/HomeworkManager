package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 클래스 종류
public enum ClassType {
    KINDERGARTEN("유아"),
    ELEMENTARY_SCHOOL("초등"),
    MIDDLE_SCHOOL("중등"),
    HIGH_SCHOOL("고등"),
    UNIVERSITY("대학"),
    ORDINARY_PERSON("일반인"),
    ;

    private final String classType;
}