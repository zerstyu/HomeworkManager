package com.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class WrongAnswerNoteResponse {
    private Long assignmentId;
    private Long assignmentFileId;
    private String subjectName;
    private String noticeTitle;
    private String feedback;
    private Integer score;
}
