package com.manager.homework.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public List<SubjectDto> getSubjectList(Long userId) {
//        querydsl test
        List<SubjectDto> subjectDtoList = subjectRepository.selectSubjectList(userId);
        subjectDtoList.forEach(System.out::println);

//        List<Subject> subjectList = subjectRepository.findByUserId(userId);

//        List<SubjectDto> subjectDtoList = Lists.newArrayList();
//
//        subjectList.forEach(
//                it -> subjectDtoList.add(
//                        SubjectDto.builder()
//                                .subjectId(it.getId())
//                                .userId(userId)
//                                .name(it.getName())
//                                .build()));

        return subjectDtoList;
    }

    public void createSubject(SubjectDto subjectDto) {
        if (isExistSubject(subjectDto.getUserId(), subjectDto.getSubjectName())) {
            throw new CustomException(ErrorCode.SUBJECT_DUPLICATION);
        }
        subjectRepository.save(convertToEntity(subjectDto));
    }

    private Subject convertToEntity(SubjectDto subjectDto) {
        User userEntityWrapper = userRepository.findById(subjectDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE));
        log.info("userEntityWrapper = {}", userEntityWrapper);
        return Subject.builder()
                .user(userEntityWrapper)
                .name(subjectDto.getSubjectName())
                .build();
    }

    public void updateSubject(SubjectDto subjectDto) {
        if (isExistSubject(subjectDto.getUserId(), subjectDto.getSubjectName())) {
            throw new CustomException(ErrorCode.SUBJECT_CHANGE_DUPLICATION);
        }
        Subject subject = subjectRepository.findByUserIdAndName(subjectDto.getUserId(), subjectDto.getSubjectName());
        subject.setName(subjectDto.getSubjectName());
        subjectRepository.save(subject);
    }

    public void deleteSubject(SubjectDto subjectDto) {
        Subject subject = subjectRepository.findByUserIdAndName(subjectDto.getUserId(), subjectDto.getSubjectName());
        if (subject == null) {
            throw new CustomException(ErrorCode.SUBJECT_REMOVE_NONE);
        }
        subjectRepository.delete(subject);
    }

    public boolean isExistSubject(Long userId, String name) {
        return subjectRepository.existsByUserIdAndName(userId, name);
    }
}
