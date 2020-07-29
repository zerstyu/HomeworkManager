package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.utils.SubjectUtils;
import com.manager.homework.vo.SearchSubjectDto;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    private String inviteUrl;
    private static final int CODE_LENGTH = 10;

    @Value("${subject.inviteUrl}")
    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public SearchSubjectDto getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public SubjectDto createSubject(Long userId, String subjectName) {
        return Subject.toDto(
                subjectRepository.save(
                        Subject.builder()
                                .user(getUser(userId))
                                .name(subjectName)
                                .inviteUrl(getInviteUrl())
                                .build()));
    }

    public void updateSubject(Long subjectId, String changeSubjectName) {
        Subject subject = getSubject(subjectId);
        subject.setName(changeSubjectName);
        subjectRepository.save(subject);
    }

    @Transactional
    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE));
    }

    private Subject getSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBJECT_NONE));
    }

    private String getInviteUrl() {
        return inviteUrl + SubjectUtils.makeRandomCode(CODE_LENGTH);
    }
}