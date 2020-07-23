package com.manager.homework.vo;

import lombok.Data;

@Data
public class FileDto {
    private Long userId;
    private Long subjectId;
    private String base64Str;
}
