package com.manager.homework.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.manager.homework.type.CategoryGroupType;
import com.manager.homework.type.CategoryType;
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
public class CategoryGroupDto {
    private CategoryGroupType categoryGroup;
    private String categoryGroupName;
    private List<CategoryDto> categoryList;

    @Builder
    @Data
    public static class CategoryDto {
        private CategoryType category;
        private String categoryName;
    }
}