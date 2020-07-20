package com.manager.homework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    OK("SUCCESS"),
    FAIL("ERROR");

    private String message;
}
