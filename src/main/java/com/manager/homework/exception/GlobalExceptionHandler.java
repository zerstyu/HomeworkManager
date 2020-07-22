package com.manager.homework.exception;

import com.manager.homework.common.Response;
import com.manager.homework.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public Response<String> handleCustomException(CustomException e) {
        log.error("{}", e.getErrorCode());
        return Response.fail(e.getErrorCode().getMessage(), e.getErrorCode());
    }

    @ExceptionHandler({Exception.class})
    public Response<String> handleUnExpectedException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return Response.fail(
                Optional.ofNullable(e.getMessage()).orElse(e.toString()), ErrorCode.NOT_DEFINED);
    }

    @ExceptionHandler({NullPointerException.class})
    public Response<String> handleNullPointerException(Exception e) {
        log.error("{}", e.getMessage(), e);
        return Response.fail(
                Optional.ofNullable(e.getMessage()).orElse(e.toString()), ErrorCode.NULL_POINT);
    }
}
