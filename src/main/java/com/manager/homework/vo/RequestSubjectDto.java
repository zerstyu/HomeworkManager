package com.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manager.homework.type.CategoryGroupType;
import com.manager.homework.type.CategoryType;
import com.manager.homework.type.ClassType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@RequiredArgsConstructor
@Data
@AllArgsConstructor
public class RequestSubjectDto {
    private String subjectName;
    private Long userId;
    private CategoryGroupType categoryGroup;
    private CategoryType category;
    private ClassType classType;
}