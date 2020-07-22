package com.manager.homework.vo;

import com.manager.homework.type.FileType;
import lombok.Data;

@Data
public class FileDto {
    private FileType type;
    private Long memberId;
    private Long subjectId;
    private String base64Str;
}
