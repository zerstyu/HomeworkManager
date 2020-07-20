package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public SubjectDto getSubjectList(String email) {
        log.debug("getSubjectList email = {}", email);
        return Optional.ofNullable(
                SubjectDto.builder()
                        .email(email)
                        .subjectList(subjectRepository.findByEmail(email))
                        .build())
                .orElse(new SubjectDto());
    }

    public void createSubject(SubjectDto subjectDto) throws Exception {
        if(isExistSubject(subjectDto.getEmail(), subjectDto.getName())) {
            throw new Exception("과목이 존재합니다.");
        }
        subjectRepository.save(subjectDto.toEntity());
    }

    public void updateSubject(SubjectDto subjectDto) throws Exception {
        if(isExistSubject(subjectDto.getEmail(), subjectDto.getChangeName())) {
            throw new Exception("과목이 존재합니다.");
        }
        Subject subject = subjectRepository.findByEmailAndName(subjectDto.getEmail(), subjectDto.getName());
        subject.setName(subjectDto.getChangeName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findByEmailAndName(subjectDto.getEmail(), subjectDto.getName());
        subjectRepository.delete(subject);
    }

    public boolean isExistSubject(String email, String name) {
        return subjectRepository.existsSubjectByEmailAndName(email, name);
    }
}
