//package com.manager.homework.repository
//
//import com.google.common.collect.Lists
//import com.manager.homework.domain.Subject
//import com.manager.homework.domain.User
//import com.manager.homework.type.Gender
//import com.manager.homework.vo.SearchSubjectDto
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
//import org.springframework.context.annotation.Profile
//import spock.lang.Specification
//
//import java.time.LocalDate
//
//@Profile("local")
//@DataJpaTest
//class SubjectRepositoryImplTest extends Specification {
//    @Autowired
//    UserRepository userRepository
//    @Autowired
//    SubjectRepositoryImpl subjectRepositoryImpl
//    @Autowired
//    SubjectRepository subjectRepository
//
//    def "test 본인이 생성한 과목 리스트 조회"() {
//        given:
//        List<User> userList = userRepository.saveAll(getUserList())
//        List<Subject> subjectList = subjectRepository.saveAll(getSubjectList())
//
//        userList.forEach({ it -> println(it) })
//        subjectList.forEach({ it -> println(it) })
//
//        when:
//        List<SearchSubjectDto> searchSubjectDtoList = subjectRepositoryImpl.selectAllSubjectList(1)
//
//        then:
//        noExceptionThrown()
//        println "------"
//        searchSubjectDtoList.forEach({ it -> println(it) })
//    }
//
//    def getUserList() {
//        List<User> userList = Lists.newArrayList()
//        userList.add(
//                User.builder()
//                        .email("admin")
//                        .password("admin")
//                        .name("admin")
//                        .groupName("HomeworkManager")
//                        .birthday(LocalDate.of(1989, 8, 12))
//                        .gender(Gender.MAN)
//                        .build())
//
//        return userList
//    }
//
//    def getSubjectList() {
//        List<Subject> subjectList = Lists.newArrayList()
//
//        subjectList.add(
//                Subject.builder()
//                        .user(getUserList().get(0))
//                        .name("수학")
//                        .inviteCode("aaaaabbbbb")
//                        .build())
//
//        return subjectList
//    }
//
//}