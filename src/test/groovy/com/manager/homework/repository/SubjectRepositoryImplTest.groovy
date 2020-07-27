package com.manager.homework.repository

import com.google.common.collect.Lists
import com.manager.homework.domain.Subject
import com.manager.homework.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import spock.lang.Specification

@Profile("local")
@SpringBootTest
class SubjectRepositoryImplTest extends Specification {
    @Autowired
    SubjectRepositoryImpl subjectRepositoryImply
    @Autowired
    SubjectRepository subjectRepository

    def "test 본인이 생성한 과목 리스트 조회"() {
        given:
        getSubjectList().forEach({ it -> println(it) })
        subjectRepository.saveAll(getSubjectList())

        when:
        List<Subject> subjectList = subjectRepositoryImply.selectAll(1)

        then:
        noExceptionThrown()
        println "------"
        subjectList.forEach({ it -> println(it) })
    }

    def getSubjectList() {
        List<Subject> subjectList = Lists.newArrayList()
        Subject subject = Subject.builder()
                .user(getUserList().get(0))
                .name("수학")
                .build()
        subjectList.add(subject)
        return subjectList
    }

    def getUserList() {
        List<User> userList = Lists.newArrayList()
        User user = User.builder()
                .email("admin")
                .password("admin")
                .name("admin")
                .groupName("HomeworkManager")
                .build()
        userList.add(user)
        return userList
    }
}