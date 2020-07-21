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

    public List<SubjectDto> getSubjectList(String memberId) {
        //querydsl test
        List<Subject> queryDslList = subjectRepository.selectAll(memberId);
        queryDslList.forEach(System.out::println);

        List<Subject> subjectList = subjectRepository.findByMemberId(memberId);
        List<SubjectDto> subjectDtoList = Lists.newArrayList();

        subjectList.forEach(
                it -> subjectDtoList.add(SubjectDto.builder()
                        .memberId(memberId)
                        .name(it.getName())
                        .build()));

        return subjectDtoList;
    }

    public void createSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getMemberId(), subjectDto.getName())) {
            throw new Exception("과목이 존재합니다.");
        }
        subjectRepository.save(subjectDto.toEntity());
    }

    public void updateSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getMemberId(), subjectDto.getChangeName())) {
            throw new Exception("변경하려는 과목이름이 존재합니다.");
        }
        Subject subject = subjectRepository.findByMemberIdAndName(subjectDto.getMemberId(), subjectDto.getName());
        subject.setName(subjectDto.getChangeName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) throws Exception {
        Subject subject = subjectRepository.findByMemberIdAndName(subjectDto.getMemberId(), subjectDto.getName());
        if (subject == null) {
            throw new Exception("삭제하려는 과목이 없습니다.");
        }
        subjectRepository.delete(subject);
    }

    public boolean isExistSubject(String memberId, String name) {
        return subjectRepository.existsByMemberIdAndName(memberId, name);
    }
}
