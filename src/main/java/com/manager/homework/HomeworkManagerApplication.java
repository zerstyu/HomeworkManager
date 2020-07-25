package com.manager.homework;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Assignment;
import com.manager.homework.domain.File;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.AssignmentRepository;
import com.manager.homework.repository.FileRepository;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
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
    private final FileRepository fileRepository;
    private final AssignmentRepository assignmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        userRepository.saveAll(getUserList());
        subjectRepository.saveAll(getSubjectList());
        fileRepository.saveAll(getFileList());
        assignmentRepository.saveAll(getAssignmentList());
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

    private List<File> getFileList(){
        List fileList = Lists.newArrayList();
        File originalFile = File.builder()
                .type(FileType.ORIGINAL)
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .build();
        fileList.add(originalFile);

        File modifiedFile = File.builder()
                .type(FileType.MODIFIED)
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .build();
        fileList.add(modifiedFile);
        return fileList;
    }

    private List<Assignment> getAssignmentList(){
        List assignmentList = Lists.newArrayList();
        Assignment assignment = Assignment.builder()
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .title("수학 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        assignmentList.add(assignment);

        Assignment assignment2 = Assignment.builder()
                .user(getUserList().get(0))
                .subject(getSubjectList().get(0))
                .title("국어 1번 과제")
                .content("첨부된 이미지를 다운받아서 문제를 풀고 제출하세요.")
                .build();
        assignmentList.add(assignment2);
        return assignmentList;
    }
}
