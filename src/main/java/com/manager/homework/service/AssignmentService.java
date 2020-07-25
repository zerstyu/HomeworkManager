package com.manager.homework.service;

import com.manager.homework.domain.Assignment;
import com.manager.homework.domain.Notice;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.*;
import com.manager.homework.vo.AssignmentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentRepositorySupport assignmentRepositorySupport;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final NoticeRepository noticeRepository;

    public List<Assignment> getAssignmentList(AssignmentDto noticeDto) {
        return assignmentRepositorySupport.findByCondition(noticeDto);
    }

    public void createAssignment(AssignmentDto assignmentDto) throws Exception {
        assignmentRepository.save(convertToEntity(assignmentDto));
    }

    public Assignment getAssignment(Long id) {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        return assignmentEntityWrapper.get();
    }

    public Assignment updateAssignment(Long id, AssignmentDto assignmentDto) throws Exception {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        Assignment assignment = assignmentEntityWrapper.get();

        Optional<User> userEntityWrapper = userRepository.findById(assignmentDto.getUserId());
        assignment.setUser(userEntityWrapper.get());

        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(assignmentDto.getSubjectId());
        assignment.setSubject(subjectEntityWrapper.get());

        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(assignmentDto.getSubjectId());
        assignment.setNotice(noticeEntityWrapper.get());

        assignment.setFeedback(assignmentDto.getFeedback());

        assignmentRepository.save(assignment);
        return assignment;
    }

    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }

    private Assignment convertToEntity(AssignmentDto assignmentDto) {
        Optional<User> userEntityWrapper = userRepository.findById(assignmentDto.getUserId());
        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(assignmentDto.getSubjectId());
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(assignmentDto.getNoticeId());

        return Assignment.builder()
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .notice(noticeEntityWrapper.get())
                .feedback(assignmentDto.getFeedback())
                .build();
    }
}