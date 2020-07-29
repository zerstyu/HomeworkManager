package com.manager.homework.service;

import com.manager.homework.domain.JoinSubject;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.JoinSubjectRepository;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.JoinSubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinSubjectService {
    private final JoinSubjectRepository joinSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public void createJoinSubject(JoinSubjectDto joinSubjectDto) {
        joinSubjectRepository.save(convertToEntity(joinSubjectDto));
    }

    public void deleteJoinSubject(JoinSubjectDto joinSubjectDto) {
        checkSubject(joinSubjectDto.getJoinSubjectId());
        joinSubjectRepository.deleteById(joinSubjectDto.getJoinSubjectId());
    }

    private JoinSubject checkSubject(Long joinSubjectId) {
        return joinSubjectRepository.findById(joinSubjectId)
                .orElseThrow(() -> new CustomException(ErrorCode.SUBJECT_NONE));
    }

    private JoinSubject convertToEntity(JoinSubjectDto joinSubjectDto) {
        return JoinSubject.builder()
                .user(userRepository.findById(joinSubjectDto.getUserId())
                        .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE)))
                .subject(subjectRepository.findById(joinSubjectDto.getSubjectId())
                        .orElseThrow(() -> new CustomException(ErrorCode.JOIN_SUBJECT_NONE)))
                .makeUser(userRepository.findById(joinSubjectDto.getMakeUserId())
                        .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE)))
                .build();
    }
}