package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.utils.SubjectUtils;
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
    private final CommonService commonService;

    private static final int CODE_LENGTH = 10;

    public SearchSubjectDto getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public SubjectDto createSubject(Long userId, String subjectName) {
        return Subject.toDto(
                subjectRepository.save(
                        Subject.builder()
                                .user(commonService.getUser(userId))
                                .name(subjectName)
                                .inviteCode(SubjectUtils.makeRandomCode(CODE_LENGTH))
                                .build()));
    }

    public void updateSubject(Long subjectId, String changeSubjectName) {
        Subject subject = commonService.getSubject(subjectId);
        subject.setName(changeSubjectName);
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}