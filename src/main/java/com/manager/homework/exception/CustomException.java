package com.manager.homework.exception;

import com.manager.homework.type.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends Exception {
    private final ErrorCode errorCode;
}
