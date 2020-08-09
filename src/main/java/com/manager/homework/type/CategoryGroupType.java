package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Getter
// 카테고리 그룹 종류
public enum CategoryGroupType {
    STUDY("학문", Arrays.asList(CategoryType.MATH, CategoryType.KOREAN, CategoryType.ENGLISH,
            CategoryType.SCIENCE, CategoryType.SOCIAL)),
    PASSING_EVENTS("시사", Arrays.asList(CategoryType.ECONOMY, CategoryType.SOCIAL)),
    COMPUTER("컴퓨터", Arrays.asList(CategoryType.WORD, CategoryType.EXCEL, CategoryType.POWERPOINT)),
    PROGRAMMING("프로그래밍", Arrays.asList(CategoryType.JAVA, CategoryType.C, CategoryType.HTML)),
    GAME("게임", Arrays.asList(CategoryType.STRATEGY, CategoryType.PUZZLE, CategoryType.QUIZ)),
    FOREIGN_LANGUAGE("외국어", Arrays.asList(CategoryType.ENGLISH, CategoryType.CHINESE,
            CategoryType.JAPANESE)),
    ART("예술", Arrays.asList(CategoryType.MUSIC, CategoryType.ART, CategoryType.PHOTO)),
    SPORTS("스포츠", Arrays.asList(CategoryType.BOWLING, CategoryType.FOOTBALL, CategoryType.BASEBALL)),
    OTHER("기타", Arrays.asList(CategoryType.CARTOON, CategoryType.OTHERS)),
    EMPTY("없음", Collections.EMPTY_LIST),
    ;

    private final String categoryGroup;
    private final List<CategoryType> categoryCodeList;

    public static CategoryGroupType findByCategoryCode(CategoryType type) {
        return Arrays.stream(CategoryGroupType.values())
                .filter(groupType -> groupType.hasCategoryType(type))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasCategoryType(CategoryType type) {
        return categoryCodeList.stream()
                .anyMatch(categoryType -> categoryType == type);
    }
}