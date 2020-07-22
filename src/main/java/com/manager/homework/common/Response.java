package com.manager.homework.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.type.StatusCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Data
public class Response<T> {
    private StatusCode statusCode;
    private String message;
    private final T data;
    private ErrorCode errorCode;

    public static <T> Response<T> ok(@Nullable T data) {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), data, null);
    }

    public static Response<String> ok() {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), null, null);
    }

    public static <T> Response<T> fail(@NonNull String message, @Nullable T data, ErrorCode errorCode) {
        return new Response<>(StatusCode.FAIL, message, data, errorCode);
    }

    public static Response<String> fail(@NonNull String message, ErrorCode errorCode) {
        return new Response<>(StatusCode.FAIL, message, null, errorCode);
    }
}
