package com.manager.homework.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
// 카테고리 종류
public enum CategoryType {
    MATH("수학"),
    KOREAN("국어"),
    ENGLISH("영어"),
    SCIENCE("과학"),
    SOCIAL("사회"),
    SOCIAL2("사회"),

    ECONOMY("경제"),

    WORD("워드"),
    EXCEL("엑셀"),
    POWERPOINT("파워포인트"),
    JAVA("Java"),
    C("C"),
    HTML("Html"),

    STRATEGY("전략"),
    PUZZLE("퍼즐"),
    QUIZ("퀴즈"),

    CHINESE("중국어"),
    JAPANESE("일본어"),

    MUSIC("뮤직"),
    ART("미술"),
    PHOTO("사진"),

    BOWLING("볼링"),
    FOOTBALL("축구"),
    BASEBALL("야구"),

    CARTOON("만화"),
    OTHERS("기타"),
    EMPTY("없음"),
    ;

    private String category;
}