package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.RequestSubjectDto;
import com.manager.homework.vo.SearchSubjectDto;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final CommonService commonService;

    private static final int MAX_MAKE_RETRY = 5;

    public List<SearchSubjectDto> getSubjectList(Long userId) {
        return subjectRepository.selectAllSubjectList(userId);
    }

    public SubjectDto createSubject(RequestSubjectDto dto) {
        return Subject.toDto(
                subjectRepository.save(
                        Subject.builder()
                                .user(commonService.getUser(dto.getUserId()))
                                .name(dto.getSubjectName())
                                .inviteCode(makeRandomCode())
                                .build()));
    }

    public void updateSubject(Long subjectId, RequestSubjectDto dto) {
        Subject subject = commonService.getSubject(subjectId);
        subject.setName(dto.getSubjectName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }

    public SubjectDto getJoinSubject(String inviteCode) {
        return Subject.toDto(
                subjectRepository.findByInviteCode(inviteCode)
                        .orElseThrow(() -> new CustomException(ErrorCode.SUBJECT_INVITE_CODE_NONE)));
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