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
public class StatisticsSubjectRangeAvgDto {
    Long subjectId;
    String subjectName;
    Long userId;
    String userName;
    Integer userRangeIndex;
    List<String> rangeList;
    List<Integer> countList;
}
