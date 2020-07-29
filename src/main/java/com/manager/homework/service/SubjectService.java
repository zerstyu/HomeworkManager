package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.SearchSubjectDto;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public SearchSubjectDto getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public void createSubject(SubjectDto subjectDto) {
        subjectRepository.save(convertToEntity(subjectDto));
    }

    public void updateSubject(SubjectDto subjectDto) {
        Subject subject = checkSubject(subjectDto.getSubjectId());
        subject.setName(subjectDto.getSubjectName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) {
        checkSubject(subjectDto.getSubjectId());
        subjectRepository.deleteById(subjectDto.getSubjectId());
    }

    private Subject checkSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBJECT_NONE));
    }

    private Subject convertToEntity(SubjectDto subjectDto) {
        User user = userRepository.findById(subjectDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE));

        return Subject.builder()
                .user(user)
                .name(subjectDto.getSubjectName())
                .build();
    }
}