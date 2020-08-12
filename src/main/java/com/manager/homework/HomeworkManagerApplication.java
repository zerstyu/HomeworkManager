package com.manager.homework;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class HomeworkManagerApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final JoinSubjectRepository joinSubjectRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentFileRepository assignmentFileRepository;
    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<User> userList = userRepository.saveAll(getUserList());
        List<Subject> subjectList = subjectRepository.saveAll(getSubjectList(userList));
        joinSubjectRepository.saveAll(getJoinSubjectList(userList, subjectList));
        List<Notice> noticeList = noticeRepository.saveAll(getNoticeList(subjectList));
        noticeFileRepository.saveAll(getNoticeFileList(noticeList));
        List<Assignment> assignmentList = assignmentRepository.saveAll(getAssignmentList(userList, noticeList));
        assignmentFileRepository.saveAll(getAssignmentFileList(assignmentList));
        List<Product> productList = productRepository.saveAll(getProductList());
    }

    private List<User> getUserList() {
        List<User> userList = Lists.newArrayList();
        String password = new BCryptPasswordEncoder().encode("admin");

        userList.add(User.builder()
                .email("admin")
                .password(password)
                .name("admin")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 8, 12))
                .gender(Gender.MAN)
                .build());

        userList.add(User.builder()
                .email("chu")
                .password(password)
                .name("chu")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 11, 10))
                .gender(Gender.MAN)
                .build());

        userList.add(User.builder()
                .email("lee")
                .password(password)
                .name("lee")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(2000, 11, 10))
                .gender(Gender.WOMAN)
                .build());

        userList.add(User.builder()
                .email("highschool@naver.com")
                .password(password)
                .name("학생")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(2002, 7, 7))
                .gender(Gender.WOMAN)
                .build());
        return userList;
    }

    private List<Subject> getSubjectList(List<User> userList) {
        List<Subject> subjectList = Lists.newArrayList();

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("수학")
                .inviteCode("aaaaabbbbb")
                .categoryGroup(CategoryGroupType.STUDY)
                .category(CategoryType.MATH)
                .classType(ClassType.ELEMENTARY_SCHOOL)
                .build());

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("국어")
                .inviteCode("cccccddddd")
                .categoryGroup(CategoryGroupType.STUDY)
                .category(CategoryType.KOREAN)
                .classType(ClassType.MIDDLE_SCHOOL)
                .build());

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("코딩")
                .inviteCode("cccccddddd")
                .categoryGroup(CategoryGroupType.PROGRAMMING)
                .category(CategoryType.JAVA)
                .classType(ClassType.MIDDLE_SCHOOL)
                .build());

        return subjectList;
    }

    private List<JoinSubject> getJoinSubjectList(List<User> userList, List<Subject> subjectList) {
        List<JoinSubject> joinSubjectList = Lists.newArrayList();

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(0))
                .user(userList.get(0))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(0))
                .user(userList.get(1))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(0))
                .user(userList.get(2))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(1))
                .user(userList.get(1))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(1))
                .user(userList.get(2))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(1))
                .user(userList.get(3))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .subject(subjectList.get(2))
                .user(userList.get(3))
                .build());

        return joinSubjectList;
    }

    private List<Notice> getNoticeList(List<Subject> subjectList) {
        List<Notice> noticeList = Lists.newArrayList();

        noticeList.add(Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.PROGRESS)
                .title("수학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(10))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(1))
                .status(NoticeStatus.PENDING)
                .title("국어 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(5))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(1))
                .status(NoticeStatus.PENDING)
                .title("국어 2번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(15))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(2))
                .status(NoticeStatus.COMPLETED)
                .title("코딩자바 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(10))
                .build());

        return noticeList;
    }

    private List<Assignment> getAssignmentList(List<User> userList, List<Notice> noticeList) {
        List<Assignment> assignmentList = Lists.newArrayList();

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("답안을 더 자세하게 써주세요. ")
                .score(75)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("망했어요. ")
                .score(25)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(1).getSubject())
                .notice(noticeList.get(1))
                .feedback("잘했어요. ")
                .score(80)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(1).getSubject())
                .notice(noticeList.get(1))
                .feedback("다시해와요. ")
                .score(10)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(1).getSubject())
                .notice(noticeList.get(1))
                .feedback("좀 더 생각해보세요. ")
                .score(60)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(2).getSubject())
                .notice(noticeList.get(2))
                .feedback("잘 했지만 조금 아쉬워요. ")
                .score(70)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(2).getSubject())
                .notice(noticeList.get(2))
                .feedback("이것도 다시 해오세요. ")
                .score(20)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(2).getSubject())
                .notice(noticeList.get(2))
                .feedback("박수 짝짝짝. ")
                .score(100)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(3).getSubject())
                .notice(noticeList.get(3))
                .feedback("좀 더 열심히 해봐요. ")
                .score(40)
                .isOpen(true)
                .build());

        return assignmentList;
    }

    private List<AssignmentFile> getAssignmentFileList(List<Assignment> assignmentList) {
        List<AssignmentFile> fileList = Lists.newArrayList();

        AssignmentFile originalAssignmentFile = AssignmentFile.builder()
                .type(FileType.ORIGINAL)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(0))
                .build();
        fileList.add(originalAssignmentFile);

        AssignmentFile modifiedAssignmentFile = AssignmentFile.builder()
                .type(FileType.MODIFIED)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(0))
                .build();
        fileList.add(modifiedAssignmentFile);
        return fileList;
    }

    private List<NoticeFile> getNoticeFileList(List<Notice> noticeList) {
        List<NoticeFile> fileList = Lists.newArrayList();

        fileList.add(NoticeFile.builder()
                .user(noticeList.get(0).getUser())
                .notice(noticeList.get(0))
                .build());

        return fileList;
    }

    private List<Product> getProductList() {
        List<Product> productList = Lists.newArrayList();

        productList.add(Product.builder()
                .name("EBS 고교 50일 수학 (상)")
                .price(7000)
                .imageUrl("http://image.yes24.com/goods/34421049/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 수학영역 수학 나형 (2020년)")
                .price(7650)
                .imageUrl("http://image.yes24.com/goods/90443884/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 수학영역 수학 가형 (2020년)")
                .price(8190)
                .imageUrl("http://image.yes24.com/goods/90443874/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능특강 수학영역 수학 1 (2020년)")
                .price(4770)
                .imageUrl("http://image.yes24.com/goods/85927922/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능특강 국어영역 문학")
                .price(8200)
                .imageUrl("http://image.yes24.com/goods/85927903/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 영어영역 영어")
                .price(9800)
                .imageUrl("http://image.yes24.com/goods/90443862/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 과학탐구영역 지구과학1")
                .price(5900)
                .imageUrl("http://image.yes24.com/goods/90444326/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 사회탐구영역 사회·문화")
                .price(6400)
                .imageUrl("http://image.yes24.com/goods/90444094/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 사회탐구영역 경제")
                .price(5700)
                .imageUrl("http://image.yes24.com/goods/74211634/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 국영수 자연계(이과) 세트")
                .price(28700)
                .imageUrl("http://image.yes24.com/goods/90445030/800x0")
                .build());

        productList.add(Product.builder()
                .name("EBS 수능완성 국영수 인문계(문과) 세트")
                .price(28100)
                .imageUrl("http://image.yes24.com/goods/90445049/800x0")
                .build());

        return productList;
    }
}
