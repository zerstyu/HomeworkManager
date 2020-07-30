package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.SearchSubjectDto;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CommonService commonService;

    private static final int MAX_MAKE_RETRY = 5;

    public SearchSubjectDto getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public SubjectDto createSubject(Long userId, String subjectName) {
        return Subject.toDto(
                subjectRepository.save(
                        Subject.builder()
                                .user(commonService.getUser(userId))
                                .name(subjectName)
                                .inviteCode(makeRandomCode())
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

    private String makeRandomCode() {
        String uuid;
        int retry = 0;

        do {
            uuid = UUID.randomUUID().toString().replace("-", "");
            retry++;

            if (retry > MAX_MAKE_RETRY) {
                throw new CustomException(ErrorCode.SUBJECT_INVITE_CODE_MAX_RETRY);
            }
        }
        while (subjectRepository.existsByInviteCode(uuid));

        return uuid;
    }
}