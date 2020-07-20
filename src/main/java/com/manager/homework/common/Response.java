package com.manager.homework.common;

import com.manager.homework.type.StatusCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Builder
@Data
public class Response<T> {
    private StatusCode code;
    private String message;
    private final T data;

    public static <T> Response<T> ok(@Nullable T data) {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), data);
    }

    public static Response<String> ok() {
        return new Response<>(StatusCode.OK, StatusCode.OK.getMessage(), "ok");
    }

    public static <T> Response fail(@NonNull String message, @Nullable T data) {
        return new Response<>(StatusCode.FAIL, message, data);
    }

    public static Response fail(@NonNull String message) {
        return new Response<>(StatusCode.FAIL, message, null);
    }
}
