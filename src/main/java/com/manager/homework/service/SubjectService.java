package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Subject;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public List<SubjectDto> getSubjectList(String email) {
        List<Subject> subjectList = subjectRepository.findByEmail(email);
        List<SubjectDto> subjectDtoList = Lists.newArrayList();

        subjectList.forEach(
                it -> subjectDtoList.add(SubjectDto.builder()
                        .email(email)
                        .name(it.getName())
                        .build()));

        return subjectDtoList;
    }

    public void createSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getEmail(), subjectDto.getName())) {
            throw new Exception("과목이 존재합니다.");
        }
        subjectRepository.save(subjectDto.toEntity());
    }

    public void updateSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getEmail(), subjectDto.getChangeName())) {
            throw new Exception("변경하려는 과목이름이 존재합니다.");
        }
        Subject subject = subjectRepository.findByEmailAndName(subjectDto.getEmail(), subjectDto.getName());
        subject.setName(subjectDto.getChangeName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) throws Exception {
        Subject subject = subjectRepository.findByEmailAndName(subjectDto.getEmail(), subjectDto.getName());
        if (subject == null) {
            throw new Exception("삭제하려는 과목이 없습니다.");
        }
        subjectRepository.delete(subject);
    }

    public boolean isExistSubject(String email, String name) {
        return subjectRepository.existsSubjectByEmailAndName(email, name);
    }
}
