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
public class StatisticsResultDto {
    private Long userId;
    private String userName;
    private Long subjectId;
    private String subjectName;
    private int totalScore;
}