package com.manager.homework.vo;

import lombok.Data;

@Data
public class CommonResponse {
    private String result;
    private String errorMessage;
    private Object data;

    public CommonResponse(String result, String errorMessage) {
        this.result = result;
        this.errorMessage = errorMessage;
    }

    public CommonResponse(String result, Object data) {
        this.result = result;
        this.data = data;
    }
}
