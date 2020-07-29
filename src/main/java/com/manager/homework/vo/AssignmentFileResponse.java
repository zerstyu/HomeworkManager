package com.manager.homework.vo;

import com.manager.homework.type.FileType;
import lombok.Data;

@Data
public class AssignmentFileResponse {
    private Long assignmentFileId;
    private FileType type;
    private String fileString;
}