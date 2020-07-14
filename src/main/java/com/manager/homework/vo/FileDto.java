package com.manager.homework.vo;

import lombok.Data;

@Data
public class FileDto {
    private Long memberId;
    private Long subjectId;
    private String base64Str;
}
