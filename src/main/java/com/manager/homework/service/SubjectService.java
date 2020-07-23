package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.SubjectDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public List<SubjectDto> getSubjectList(Long userId) {
        //querydsl test
        List<Subject> queryDslList = subjectRepository.selectAll(userId);
        queryDslList.forEach(System.out::println);

        List<Subject> subjectList = subjectRepository.findByUserId(userId);
        List<SubjectDto> subjectDtoList = Lists.newArrayList();

        subjectList.forEach(
                it -> subjectDtoList.add(SubjectDto.builder()
                        .userId(userId)
                        .name(it.getName())
                        .build()));

        return subjectDtoList;
    }

    public void createSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getUserId(), subjectDto.getName())) {
            throw new CustomException(ErrorCode.SUBJECT_DUPLICATION);
        }
        subjectRepository.save(convertToEntity(subjectDto));
    }

    private Subject convertToEntity(SubjectDto subjectDto) {
        Optional<User> userEntityWrapper = userRepository.findById(subjectDto.getUserId());

        return Subject.builder()
                .user(userEntityWrapper.get())
                .name(subjectDto.getName())
                .build();
    }

    public void updateSubject(SubjectDto subjectDto) throws Exception {
        if (isExistSubject(subjectDto.getUserId(), subjectDto.getChangeName())) {
            throw new CustomException(ErrorCode.SUBJECT_CHANGE_DUPLICATION);
        }
        Subject subject = subjectRepository.findByUserIdAndName(subjectDto.getUserId(), subjectDto.getName());
        subject.setName(subjectDto.getChangeName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) throws Exception {
        Subject subject = subjectRepository.findByUserIdAndName(subjectDto.getUserId(), subjectDto.getName());
        if (subject == null) {
            throw new CustomException(ErrorCode.SUBJECT_REMOVE_NONE);
        }
        subjectRepository.delete(subject);
    }

    public boolean isExistSubject(Long userId, String name) {
        return subjectRepository.existsByUserIdAndName(userId, name);
    }
}
