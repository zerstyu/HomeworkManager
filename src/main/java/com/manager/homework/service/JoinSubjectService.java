package com.manager.homework.service;

import com.manager.homework.domain.JoinSubject;
import com.manager.homework.repository.JoinSubjectRepository;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JoinSubjectService {
    private final JoinSubjectRepository joinSubjectRepository;
    private final CommonService commonService;

    public SubjectDto createJoinSubject(Long subjectId, Long userId) {
        return JoinSubject.toDto(
                joinSubjectRepository.save(
                        JoinSubject.builder()
                                .user(commonService.getUser(userId))
                                .subject(commonService.getSubject(subjectId))
                                .build()));
    }

    public void deleteJoinSubject(Long joinSubjectId) {
        joinSubjectRepository.deleteById(joinSubjectId);
    }
}