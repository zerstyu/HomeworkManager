package com.manager.homework;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.type.FileType;
import com.manager.homework.type.Gender;
import com.manager.homework.type.NoticeStatus;
import com.manager.homework.type.NoticeType;
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
    private final OrderRepository orderRepository;

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
        List<Assignment> assignmentList = assignmentRepository.saveAll(getAssignmentList(noticeList));
        assignmentFileRepository.saveAll(getAssignmentFileList(assignmentList));
        List<Product> productList = productRepository.saveAll(getProductList());
        orderRepository.saveAll(getOrderList(userList, productList));
    }

    private List<User> getUserList() {
        List userList = Lists.newArrayList();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("admin");

        User user = User.builder()
                .email("admin")
                .password(password)
                .name("admin")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 8, 12))
                .gender(Gender.MAN)
                .build();
        userList.add(user);

        userList.add(User.builder()
                .email("chu@naver.com")
                .password("chu")
                .name("chu")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(1989, 11, 10))
                .gender(Gender.MAN)
                .build());

        userList.add(User.builder()
                .email("lee@naver.com")
                .password("lee")
                .name("lee")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(2000, 11, 10))
                .gender(Gender.WOMAN)
                .build());

        userList.add(User.builder()
                .email("highschool@naver.com")
                .password("high")
                .name("학생")
                .groupName("HomeworkManager")
                .birthday(LocalDate.of(2002, 7, 7))
                .gender(Gender.WOMAN)
                .build());
        return userList;
    }

    private List<Subject> getSubjectList(List<User> userList) {
        List subjectList = Lists.newArrayList();

        subjectList.add(Subject.builder()
                .user(userList.get(0))
                .name("수학")
                .inviteCode("aaaaabbbbb")
                .build());

        subjectList.add(Subject.builder()
                .user(userList.get(2))
                .name("국어")
                .inviteCode("cccccddddd")
                .build());

        return subjectList;
    }

    private List<JoinSubject> getJoinSubjectList(List<User> userList, List<Subject> subjectList) {
        List joinSubjectList = Lists.newArrayList();

        joinSubjectList.add(JoinSubject.builder()
                .user(userList.get(0))
                .subject(subjectList.get(0))
                .user(userList.get(1))
                .build());

        joinSubjectList.add(JoinSubject.builder()
                .user(userList.get(0))
                .subject(subjectList.get(0))
                .user(userList.get(2))
                .build());

        return joinSubjectList;
    }

    private List<Notice> getNoticeList(List<Subject> subjectList) {
        List noticeList = Lists.newArrayList();
        Notice notice = Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.PROGRESS)
                .title("수학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(10))
                .build();
        noticeList.add(notice);

        Notice notice2 = Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.PENDING)
                .title("국어 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(5))
                .build();
        noticeList.add(notice2);

        Notice notice3 = Notice.builder()
                .type(NoticeType.PRIVATE)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.PENDING)
                .title("국어 2번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).plusDays(15))
                .build();
        noticeList.add(notice3);

        Notice notice4 = Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.COMPLETED)
                .title("과학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(10))
                .build();
        noticeList.add(notice4);

        Notice notice5 = Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.COMPLETED)
                .title("과학 2번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(5))
                .build();
        noticeList.add(notice5);

        Notice notice6 = Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.COMPLETED)
                .title("과학 3번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(7))
                .build();
        noticeList.add(notice6);

        Notice notice7 = Notice.builder()
                .type(NoticeType.OPEN)
                .user(subjectList.get(1).getUser())
                .subject(subjectList.get(0))
                .status(NoticeStatus.COMPLETED)
                .title("과학 3번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .expiredAt(LocalDate.from(LocalDate.now()).minusDays(7))
                .build();
        noticeList.add(notice7);
        return noticeList;
    }

    private List<Assignment> getAssignmentList(List<Notice> noticeList) {
        List assignmentList = Lists.newArrayList();
        Assignment assignment = Assignment.builder()
                .user(noticeList.get(0).getUser())
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("답안을 더 자세하게 써주세요. ")
                .score(75)
                .isOpen(true)
                .build();
        assignmentList.add(assignment);

        assignmentList.add(
                Assignment.builder()
                        .user(noticeList.get(0).getUser())
                        .subject(noticeList.get(0).getSubject())
                        .notice(noticeList.get(1))
                        .feedback("망했어요. ")
                        .score(25)
                        .isOpen(true)
                        .build());

        assignmentList.add(
                Assignment.builder()
                        .user(noticeList.get(6).getUser())
                        .subject(noticeList.get(0).getSubject())
                        .notice(noticeList.get(0))
                        .feedback("잘했어요. ")
                        .score(80)
                        .isOpen(true)
                        .build());

        assignmentList.add(
                Assignment.builder()
                        .user(noticeList.get(6).getUser())
                        .subject(noticeList.get(0).getSubject())
                        .notice(noticeList.get(6))
                        .feedback("다시해와요. ")
                        .score(10)
                        .isOpen(true)
                        .build());

        return assignmentList;
    }

    private List<AssignmentFile> getAssignmentFileList(List<Assignment> assignmentList) {
        List fileList = Lists.newArrayList();
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
        List fileList = Lists.newArrayList();
        NoticeFile noticeFile = NoticeFile.builder()
                .user(noticeList.get(0).getUser())
                .notice(noticeList.get(0))
                .build();
        fileList.add(noticeFile);
        return fileList;
    }

    private List<Product> getProductList() {
        List productList = Lists.newArrayList();
        Product product = Product.builder()
                .name("EBS 고교 50일 수학 (상)")
                .price(7000)
                .imageUrl("http://image.yes24.com/goods/34421049/800x0")
                .build();
        productList.add(product);

        Product product2 = Product.builder()
                .name("EBS 수능특강 국어영역 문학")
                .price(8200)
                .imageUrl("http://image.yes24.com/goods/85927903/800x0")
                .build();
        productList.add(product2);

        Product product3 = Product.builder()
                .name("EBS 수능완성 영어영역 영어")
                .price(9800)
                .imageUrl("http://image.yes24.com/goods/90443862/800x0")
                .build();
        productList.add(product3);

        Product product4 = Product.builder()
                .name("EBS 수능완성 과학탐구영역 지구과학1")
                .price(5900)
                .imageUrl("http://image.yes24.com/goods/90444326/800x0")
                .build();
        productList.add(product4);

        Product product5 = Product.builder()
                .name("EBS 수능완성 사회탐구영역 사회·문화")
                .price(6400)
                .imageUrl("http://image.yes24.com/goods/90444094/800x0")
                .build();
        productList.add(product5);

        Product product6 = Product.builder()
                .name("EBS 수능완성 사회탐구영역 경제")
                .price(5700)
                .imageUrl("http://image.yes24.com/goods/74211634/800x0")
                .build();
        productList.add(product6);

        Product product7 = Product.builder()
                .name("EBS 수능완성 국영수 자연계(이과) 세트")
                .price(28700)
                .imageUrl("http://image.yes24.com/goods/90445030/800x0")
                .build();
        productList.add(product7);

        Product product8 = Product.builder()
                .name("EBS 수능완성 국영수 인문계(문과) 세트")
                .price(28100)
                .imageUrl("http://image.yes24.com/goods/90445049/800x0")
                .build();
        productList.add(product8);


        return productList;
    }

    private List<Order> getOrderList(List<User> userList, List<Product> productList) {
        List orderList = Lists.newArrayList();

        orderList.add(Order.builder()
                .user(userList.get(1))
                .product(productList.get(0))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(2))
                .product(productList.get(0))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(0))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(1))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(2))
                .product(productList.get(1))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(1))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(2))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(2))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(3))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(4))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(5))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(6))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(0))
                .product(productList.get(7))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(1))
                .product(productList.get(4))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(1))
                .product(productList.get(6))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(4))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(5))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(6))
                .build());

        orderList.add(Order.builder()
                .user(userList.get(3))
                .product(productList.get(7))
                .build());

        return orderList;
    }
}
