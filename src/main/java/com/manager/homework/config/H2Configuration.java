package com.manager.homework.config;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.type.*;
import lombok.RequiredArgsConstructor;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Configuration
//@Profile("local")
@RequiredArgsConstructor
public class H2Configuration {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final JoinSubjectRepository joinSubjectRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentFileRepository assignmentFileRepository;
    private final ProductRepository productRepository;
    private final WrongAnswerNoteRepository wrongAnswerNoteRepository;

    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer().start();
    }

    @Bean
    public void setTestData() {
        List<User> userList = userRepository.saveAll(getUserList());
        List<Subject> subjectList = subjectRepository.saveAll(getSubjectList(userList));
        joinSubjectRepository.saveAll(getJoinSubjectList(userList, subjectList));
        List<Notice> noticeList = noticeRepository.saveAll(getNoticeList(subjectList));
        noticeFileRepository.saveAll(getNoticeFileList(noticeList));
        List<Assignment> assignmentList = assignmentRepository.saveAll(getAssignmentList(userList, noticeList));
        List<AssignmentFile> assignmentFileList = assignmentFileRepository.saveAll(getAssignmentFileList(assignmentList));
        List<Product> productList = productRepository.saveAll(getProductList());
        wrongAnswerNoteRepository.saveAll(getWrongAnswerNoteList(userList, assignmentFileList));
    }

    private List<User> getUserList() {
        List<User> userList = Lists.newArrayList();
        String password = new BCryptPasswordEncoder().encode("admin");

        userList.add(User.builder()
                .email("admin")
                .password(password)
                .name("유재석")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 8, 12))
                .gender(Gender.MAN)
                .build());

        userList.add(User.builder()
                .email("chu")
                .password(password)
                .name("추대윤")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 11, 10))
                .gender(Gender.MAN)
                .build());

        userList.add(User.builder()
                .email("lee")
                .password(password)
                .name("이태호")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(2000, 11, 10))
                .gender(Gender.WOMAN)
                .build());

        userList.add(User.builder()
                .email("ahn")
                .password(password)
                .name("안재홍")
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
                .name("독후감")
                .inviteCode("aaaaabbbbb")
                .categoryGroup(CategoryGroupType.STUDY)
                .category(CategoryType.KOREAN)
                .classType(ClassType.ELEMENTARY_SCHOOL)
                .build());

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("그림일기")
                .inviteCode("cccccddddd")
                .categoryGroup(CategoryGroupType.STUDY)
                .category(CategoryType.KOREAN)
                .classType(ClassType.ELEMENTARY_SCHOOL)
                .build());

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("미술")
                .inviteCode("cccccddddd")
                .categoryGroup(CategoryGroupType.ART)
                .category(CategoryType.ART)
                .classType(ClassType.ELEMENTARY_SCHOOL)
                .build());

        return subjectList;
    }

    private List<JoinSubject> getJoinSubjectList(List<User> userList, List<Subject> subjectList) {
        List<JoinSubject> joinSubjectList = Lists.newArrayList();

//        joinSubjectList.add(JoinSubject.builder()
//                .subject(subjectList.get(0))
//                .user(userList.get(0))
//                .build());

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
                .user(userList.get(2))
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
                .status(NoticeStatus.COMPLETED)
                .title("독후감 1번 어린왕자 숙제")
                .content("어린왕자를 읽고 독후감을 제출해주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(10))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.PROGRESS)
                .title("독후감 2번 백설공주 숙제")
                .content("백설공주를 읽고 독후감을 제출해주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(20))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(1))
                .status(NoticeStatus.PENDING)
                .title("그림일기 첫번째 주 숙제")
                .content("작성한 그림일기를 올려주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(5))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(1))
                .status(NoticeStatus.PENDING)
                .title("그림일기 두번째 주 숙제")
                .content("작성한 그림일기를 올려주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(15))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(2))
                .status(NoticeStatus.COMPLETED)
                .title("미술 1번 숙제")
                .content("화목한 집을 그려주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(10))
                .build());

        noticeList.add(Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(2))
                .status(NoticeStatus.PROGRESS)
                .title("미술 2번 숙제")
                .content("가장 친한 친구를 그려주세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(15))
                .build());

        return noticeList;
    }

    private List<Assignment> getAssignmentList(List<User> userList, List<Notice> noticeList) {
        List<Assignment> assignmentList = Lists.newArrayList();

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("자신의 느낌을 더 적어주세요.")
                .score(80)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("잘 적었어요~")
                .score(90)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(1).getSubject())
                .notice(noticeList.get(1))
                .feedback("좀 더 길게 적어주세요.")
                .score(70)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(1).getSubject())
                .notice(noticeList.get(1))
                .feedback("읽기 좋네요")
                .score(95)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(2).getSubject())
                .notice(noticeList.get(2))
                .feedback("띄어쓰기에 신경써주세요.")
                .score(90)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(2).getSubject())
                .notice(noticeList.get(2))
                .feedback("참 잘 썼어요. ")
                .score(100)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(3).getSubject())
                .notice(noticeList.get(3))
                .feedback("엄마 아빠가 좋아 하시겠어요. ")
                .score(95)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(1))
                .subject(noticeList.get(3).getSubject())
                .notice(noticeList.get(3))
                .feedback("좀 더 세밀함이 필요해요. ")
                .score(70)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(4).getSubject())
                .notice(noticeList.get(4))
                .feedback("미래의 미술가가 되겠어요.! ")
                .score(100)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(4).getSubject())
                .notice(noticeList.get(5))
                .feedback("색칠을 좀 더 잘 해주세요. ")
                .score(80)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(2))
                .subject(noticeList.get(4).getSubject())
                .notice(noticeList.get(5))
                .feedback("좀 더 깨끗하게 그어주세요. ")
                .score(60)
                .isOpen(true)
                .build());

        assignmentList.add(Assignment.builder()
                .user(userList.get(3))
                .subject(noticeList.get(5).getSubject())
                .notice(noticeList.get(5))
                .feedback("좀 더 열심히 해봐요. ")
                .score(40)
                .isOpen(true)
                .build());

        return assignmentList;
    }

    private List<AssignmentFile> getAssignmentFileList(List<Assignment> assignmentList) {
        List<AssignmentFile> fileList = Lists.newArrayList();

        fileList.add(AssignmentFile.builder()
                .type(FileType.ORIGINAL)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(0))
                .build());

        fileList.add(AssignmentFile.builder()
                .type(FileType.MODIFIED)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(0))
                .build());

        fileList.add(AssignmentFile.builder()
                .type(FileType.ORIGINAL)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(1))
                .build());

        fileList.add(AssignmentFile.builder()
                .type(FileType.MODIFIED)
                .user(assignmentList.get(0).getUser())
                .assignment(assignmentList.get(1))
                .build());

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
                .name("응가 할 시간이야, 크롱!")
                .price(7000)
                .imageUrl("http://image.yes24.com/Goods/4410502/L")
                .build());

        productList.add(Product.builder()
                .name("읽어주는 동화책 003. 방귀 로켓")
                .price(7650)
                .imageUrl("http://image.yes24.com/Goods/33127064/L")
                .build());

        productList.add(Product.builder()
                .name("읽어주는 동화책 001. 나뭇잎 아이")
                .price(8190)
                .imageUrl("http://image.yes24.com/Goods/32734400/L")
                .build());

        productList.add(Product.builder()
                .name("아이랑 이야기하며 함께 보는 동화책 1")
                .price(4770)
                .imageUrl("http://image.yes24.com/Goods/93216384/L")
                .build());

        productList.add(Product.builder()
                .name("동화책을 먹은 바둑이")
                .price(8200)
                .imageUrl("http://image.yes24.com/Goods/175991/L")
                .build());

        productList.add(Product.builder()
                .name("갈매기에게 나는 법을 가르쳐준 고양이")
                .price(9800)
                .imageUrl("http://image.yes24.com/Goods/17739882/L")
                .build());

        productList.add(Product.builder()
                .name("악몽을 먹고 자란 소년")
                .price(5900)
                .imageUrl("http://image.yes24.com/Goods/90964086/L")
                .build());

        productList.add(Product.builder()
                .name("좀비 아이")
                .price(6400)
                .imageUrl("http://image.yes24.com/Goods/90964097/L")
                .build());

        productList.add(Product.builder()
                .name("수상한 화장실")
                .price(5700)
                .imageUrl("http://image.yes24.com/Goods/89496128/L")
                .build());

        productList.add(Product.builder()
                .name("마법의 설탕 두 조각")
                .price(28700)
                .imageUrl("http://image.yes24.com/Goods/201210/L")
                .build());

        productList.add(Product.builder()
                .name("너의 운명은")
                .price(28100)
                .imageUrl("http://image.yes24.com/Goods/91879000/L")
                .build());

        return productList;
    }

    private List<WrongAnswerNote> getWrongAnswerNoteList(List<User> userList, List<AssignmentFile> assignmentFileList) {
        List<WrongAnswerNote> wrongAnswerNoteList = Lists.newArrayList();

        wrongAnswerNoteList.add(WrongAnswerNote.builder()
                .user(userList.get(0))
                .assignmentFile(assignmentFileList.get(1))
                .build());

        wrongAnswerNoteList.add(WrongAnswerNote.builder()
                .user(userList.get(0))
                .assignmentFile(assignmentFileList.get(3))
                .build());

        return wrongAnswerNoteList;
    }
}