package com.manager.homework;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class HomeworkManagerApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentFileRepository assignmentFileRepository;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<User> userList = userRepository.saveAll(getUserList());
        List<Subject> subjectList = subjectRepository.saveAll(getSubjectList(userList));
        List<Notice> noticeList = noticeRepository.saveAll(getNoticeList(subjectList));
        noticeFileRepository.saveAll(getNoticeFileList(noticeList));
        List<Assignment> assignmentList = assignmentRepository.saveAll(getAssignmentList(noticeList));
        assignmentFileRepository.saveAll(getAssignmentFileList(assignmentList));
    }

    private List<User> getUserList(){
        List userList = Lists.newArrayList();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("admin");

        User user = User.builder()
                .email("admin")
                .password(password)
                .name("admin")
                .groupName("HomeworkManager")
                .build();
        userList.add(user);
        return userList;
    }

    private List<Subject> getSubjectList(List<User> userList){
        List subjectList = Lists.newArrayList();
        Subject subject = Subject.builder()
                .user(userList.get(0))
                .name("수학")
                .build();
        subjectList.add(subject);
        return subjectList;
    }

    private List<Notice> getNoticeList(List<Subject> subjectList){
        List noticeList = Lists.newArrayList();
        Notice notice = Notice.builder()
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .title("수학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        noticeList.add(notice);

        Notice notice2 = Notice.builder()
                .user(subjectList.get(0).getUser())
                .subject(subjectList.get(0))
                .title("국어 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        noticeList.add(notice2);
        return noticeList;
    }

    private List<Assignment> getAssignmentList(List<Notice> noticeList){
        List assignmentList = Lists.newArrayList();
        Assignment assignment = Assignment.builder()
                .user(noticeList.get(0).getUser())
                .subject(noticeList.get(0).getSubject())
                .notice(noticeList.get(0))
                .feedback("답안을 더 자세하게 써주세요. ")
                .grade("B")
                .build();
        assignmentList.add(assignment);
        return assignmentList;
    }

    private List<AssignmentFile> getAssignmentFileList(List<Assignment> assignmentList){
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

    private List<NoticeFile> getNoticeFileList(List<Notice> noticeList){
        List fileList = Lists.newArrayList();
        NoticeFile noticeFile = NoticeFile.builder()
                .user(noticeList.get(0).getUser())
                .notice(noticeList.get(0))
                .build();
        fileList.add(noticeFile);
        return fileList;
    }
}
