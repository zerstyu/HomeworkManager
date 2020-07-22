package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_DEFINED("A001", "정의 되지 않은 에러입니다."),
    NULL_POINT("A002", "null point 에러 입니다."),

    // Member
    EMAIL_DUPLICATION("M001", "동일한 이메일이 존재합니다."),
    LOGIN_INPUT_INVALID("M002", "로그인 입력 값이 잘못 되었습니다."),
    LOGIN_MEMBER_NONE("M003", "존재하지 않는 사용자입니다."),
    LOGIN_FAIL("M004", "로그인이 실패하였습니다."),
    // Subject
    SUBJECT_DUPLICATION("S001", "동일한 과목이 존재합니다."),
    SUBJECT_CHANGE_DUPLICATION("S002", "변경하려는 과목이 존재합니다."),
    SUBJECT_REMOVE_NONE("S003", "삭제하려는 과목이 없습니다.");

    private final String code;
    private final String message;
}
