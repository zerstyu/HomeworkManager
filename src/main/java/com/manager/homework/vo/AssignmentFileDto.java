package com.manager.homework.vo;

import com.manager.homework.type.FileType;
import lombok.Data;

@Data
public class AssignmentFileDto {
    private Long assignmentFileId;
    private FileType type;
    private Long userId;
    private Long assignmentId;
    private String fileString;
    private String historyFileString;
}
