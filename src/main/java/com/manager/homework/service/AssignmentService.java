package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.*;
import com.manager.homework.repository.*;
import com.manager.homework.vo.AssignmentDto;
import com.manager.homework.vo.AssignmentFileResponse;
import com.manager.homework.vo.AssignmentResponse;
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
    private final AssignmentFileRepository assignmentFileRepository;

    public List<Assignment> getAssignmentList(AssignmentDto assignmentDto) {
        return assignmentRepositorySupport.findByCondition(assignmentDto);
    }

    public Assignment createAssignment(AssignmentDto assignmentDto) {
        return assignmentRepository.save(convertToEntity(assignmentDto));
    }

    public AssignmentResponse getAssignment(Long id) {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        return convertToResponse(assignmentEntityWrapper.get());
    }

    public Assignment updateAssignment(Long id, AssignmentDto assignmentDto) {
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(id);
        Assignment assignment = assignmentEntityWrapper.get();

        if (assignmentDto.getUserId() != null) {
            Optional<User> userEntityWrapper = userRepository.findById(assignmentDto.getUserId());
            assignment.setUser(userEntityWrapper.get());
        }

        if (assignmentDto.getSubjectId() != null) {
            Optional<Subject> subjectEntityWrapper = subjectRepository.findById(assignmentDto.getSubjectId());
            assignment.setSubject(subjectEntityWrapper.get());
        }

        if (assignmentDto.getNoticeId() != null) {
            Optional<Notice> noticeEntityWrapper = noticeRepository.findById(assignmentDto.getNoticeId());
            assignment.setNotice(noticeEntityWrapper.get());
        }

        if (assignmentDto.getIsOpen() != null) {
            assignment.setIsOpen(assignmentDto.getIsOpen());
        }

        if (assignmentDto.getScore() != null) {
            assignment.setScore(assignmentDto.getScore());
        }

        assignment.setFeedback(assignmentDto.getFeedback());
        assignment.setNote(assignmentDto.getNote());

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
                .isOpen(assignmentDto.getIsOpen())
                .score(assignmentDto.getScore())
                .note(assignmentDto.getNote())
                .build();
    }

    private AssignmentResponse convertToResponse(Assignment assignment) {
        AssignmentResponse assignmentResponse = new AssignmentResponse();
        assignmentResponse.setAssignmentId(assignment.getId());
        assignmentResponse.setFeedback(assignment.getFeedback());
        assignmentResponse.setScore(assignment.getScore());
        assignmentResponse.setIsOpen(assignment.getIsOpen());
        assignmentResponse.setNote(assignment.getNote());
        assignmentResponse.setUserId(assignment.getUser().getId());
        assignmentResponse.setUserName(assignment.getUser().getName());
        assignmentResponse.setSubjectId(assignment.getSubject().getId());
        assignmentResponse.setSubjectName(assignment.getSubject().getName());
        assignmentResponse.setNoticeId(assignment.getNotice().getId());
        assignmentResponse.setNoticeTitle(assignment.getNotice().getTitle());
        assignmentResponse.setAssignmentFileList(getFileResponseList(assignment.getId()));
        return assignmentResponse;
    }

    private List<AssignmentFileResponse> getFileResponseList(Long assigmentId) {
        List<AssignmentFileResponse> assignmentFileResponseList = Lists.newArrayList();
        List<AssignmentFile> assignmentFileList = assignmentFileRepository.findByAssignmentId(assigmentId);
        for (AssignmentFile assignmentFile : assignmentFileList) {
            AssignmentFileResponse assignmentFileResponse = new AssignmentFileResponse();
            assignmentFileResponse.setAssignmentFileId(assignmentFile.getId());
            assignmentFileResponse.setType(assignmentFile.getType());
            assignmentFileResponse.setFileString(assignmentFile.getFileString());
            assignmentFileResponse.setHistoryFileString(assignmentFile.getHistoryFileString());
            assignmentFileResponseList.add(assignmentFileResponse);
        }
        return assignmentFileResponseList;
    }
}