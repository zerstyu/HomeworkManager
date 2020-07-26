package com.manager.homework;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.type.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class HomeworkManagerApplication implements CommandLineRunner {
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final NoticeRepository noticeRepository;
    private final AssignmentRepository assignmentRepository;
    private final AssignmentFileRepository assignmentFileRepository;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userRepository.saveAll(getUserList());
        subjectRepository.saveAll(getSubjectList());
        noticeRepository.saveAll(getNoticeList());
        assignmentRepository.saveAll(getAssignmentList());
        assignmentFileRepository.saveAll(getFileList());
    }

    private List<User> getUserList(){
        List userList = Lists.newArrayList();
        User user = User.builder()
                .email("admin")
                .password("admin")
                .name("admin")
                .groupName("HomeworkManager")
                .build();
        userList.add(user);
        return userList;
    }

    private List<Subject> getSubjectList(){
        List subjectList = Lists.newArrayList();
        Subject subject = Subject.builder()
                .user(getUserList().get(0))
                .name("수학")
                .build();
        subjectList.add(subject);
        return subjectList;
    }

    private List<Notice> getNoticeList(){
        List noticeList = Lists.newArrayList();
        Notice notice = Notice.builder()
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .title("수학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        noticeList.add(notice);

        Notice notice2 = Notice.builder()
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .title("국어 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        noticeList.add(notice2);
        return noticeList;
    }

    private List<Assignment> getAssignmentList(){
        List assignmentList = Lists.newArrayList();
        Assignment assignment = Assignment.builder()
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .notice(getNoticeList().get(0))
                .feedback("답안을 더 자세하게 써주세요. ")
                .grade("B")
                .build();
        assignmentList.add(assignment);
        return assignmentList;
    }

    private List<AssignmentFile> getFileList(){
        List fileList = Lists.newArrayList();
        AssignmentFile originalAssignmentFile = AssignmentFile.builder()
                .type(FileType.ORIGINAL)
                .user(getUserList().get(0))
                .assignment(getAssignmentList().get(0))
                .build();
        fileList.add(originalAssignmentFile);

        AssignmentFile modifiedAssignmentFile = AssignmentFile.builder()
                .type(FileType.MODIFIED)
                .user(getUserList().get(0))
                .assignment(getAssignmentList().get(0))
                .build();
        fileList.add(modifiedAssignmentFile);
        return fileList;
    }
}
