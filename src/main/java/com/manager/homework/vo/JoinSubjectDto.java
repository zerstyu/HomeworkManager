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
public class JoinSubjectDto {
    private Long joinSubjectId;
    private Long subjectId;
    private String subjectName;
    private Long userId;
    private String userName;
    private Long makeUserId;
    private Long makeUserName;
    private String inviteUrl;
}