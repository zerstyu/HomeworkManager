package com.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class SubjectResponse {
    private Long userId;
    private String userName;
    private List<SubjectDto> subjectDtoList;
    private List<SubjectDto> joinSubjectDtoList;
}