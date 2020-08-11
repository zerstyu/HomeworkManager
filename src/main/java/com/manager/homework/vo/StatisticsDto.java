package com.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manager.homework.type.CategoryGroupType;
import com.manager.homework.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class StatisticsDto {
    private Long userId;
    private String userName;
    private Long subjectId;
    private String subjectName;
    private Long noticeId;
    private String title;
    private Integer totalScore;
    private Double averageScore;
    private CategoryGroupType categoryGroup;
    private CategoryType category;
}