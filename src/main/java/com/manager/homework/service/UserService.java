package com.manager.homework.service;

import com.manager.homework.domain.User;
import com.manager.homework.exception.CustomException;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.repository.UserRepositorySupport;
import com.manager.homework.type.ErrorCode;
import com.manager.homework.vo.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserRepositorySupport userRepositorySupport;

    public List<User> getUserList(UserDto userDto) {
        return userRepositorySupport.findByCondition(userDto);
    }

    public User createUser(UserDto userDto) throws Exception {
        if (isExistUser(userDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }
        userDto.setPassword(encodePassword(userDto.getPassword()));
        return userRepository.save(userDto.toEntity());
    }

    public User getUser(Long id) {
        Optional<User> userEntityWrapper = userRepository.findById(id);
        return userEntityWrapper.get();
    }

    public User updateUser(Long id, UserDto userDto) throws Exception {
        if (isExistUser(userDto.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATION);
        }

        Optional<User> userEntityWrapper = userRepository.findById(id);
        User userEntity = userEntityWrapper.get();

        userEntity.setEmail(userDto.getEmail());
        userEntity.setGroupName(userDto.getGroupName());
        userEntity.setPassword(encodePassword(userDto.getPassword()));

        userRepository.save(userEntity);
        return userEntity;
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User loginUser(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException(ErrorCode.LOGIN_USER_NONE);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        return user;
    }

    private boolean isExistUser(String email) {
        return userRepository.existsByEmail(email);
    }

    private String encodePassword(String password) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
