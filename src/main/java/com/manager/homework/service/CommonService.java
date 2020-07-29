package com.manager.homework.service;

import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonService {
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.LOGIN_USER_NONE));
    }

    public Subject getSubject(Long subjectId) {
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new CustomException(ErrorCode.SUBJECT_NONE));
    }
}
