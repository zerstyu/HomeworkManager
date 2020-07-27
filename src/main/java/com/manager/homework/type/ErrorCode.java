package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public enum ErrorCode {
    // COMMON
    NOT_DEFINED("A001", "정의 되지 않은 에러입니다."),
    NULL_POINT("A002", "null point 에러 입니다."),

    // USER
    EMAIL_DUPLICATION("U001", "동일한 이메일이 존재합니다."),
    LOGIN_INPUT_INVALID("U002", "이메일, 비밀번호를 확인해주세요."),
    LOGIN_USER_NONE("U003", "존재하지 않는 사용자입니다."),
    LOGIN_FAIL("U004", "로그인이 실패하였습니다."),

    // Subject
    SUBJECT_NONE("S002", "과목이 존재하지 않습니다.");

    private final String code;
    private final String message;
}
