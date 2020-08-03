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
public class StatisticsDto {
    private Long userId;
    private String userName;
    private int totalScore;
}