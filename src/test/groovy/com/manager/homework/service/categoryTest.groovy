package com.manager.homework.service


import com.manager.homework.type.CategoryGroupType
import com.manager.homework.type.CategoryType
import com.manager.homework.vo.CategoryGroupDto
import spock.lang.Specification

class categoryTest extends Specification {

    def "카테고리 Type 으로 카테고리 그룹 정보 가져오기"() {
        given:
        CategoryType categoryType = TYPE

        when:
        CategoryGroupType categoryGroup = CategoryGroupType.findByCategoryCode(categoryType)

        println "categoryGroup = " + categoryGroup.name()
        println "categoryGroup = " + categoryGroup.getCategoryGroup()
        println "categoryGroup = " + CategoryType.MATH.getCategory()

        then:
        categoryGroup.name() == GROUP.name()
        noExceptionThrown()

        where:
        TYPE               | GROUP
        CategoryType.MATH  | CategoryGroupType.STUDY
        CategoryType.PHOTO | CategoryGroupType.ART

    }

    def "카테고리 리스트 가져오기"() {
        given:
        SubjectService subjectService = new SubjectService(null, null)

        when:
        List<CategoryGroupDto> categoryGroupDtoList = subjectService.getCategoryList()

        categoryGroupDtoList.stream()
                .forEach({ it -> println it })

        then:
        categoryGroupDtoList.size() > 0
        categoryGroupDtoList[0].getCategoryList().size() > 0
    }
}
