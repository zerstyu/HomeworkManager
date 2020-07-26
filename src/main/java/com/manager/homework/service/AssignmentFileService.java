package com.manager.homework.service;

import com.manager.homework.domain.Assignment;
import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.domain.User;
import com.manager.homework.repository.AssignmentFileRepository;
import com.manager.homework.repository.AssignmentFileRepositorySupport;
import com.manager.homework.repository.AssignmentRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.vo.AssignmentFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentFileService {
    private final AssignmentFileRepository assignmentFileRepository;
    private final AssignmentFileRepositorySupport assignmentFileRepositorySupport;
    private final UserRepository userRepository;
    private final AssignmentRepository assignmentRepository;

    public List<AssignmentFile> getAssignmentFileList(AssignmentFileDto assignmentFileDto) {
        return assignmentFileRepositorySupport.findByCondition(assignmentFileDto);
    }

    public AssignmentFile createAssignmentFile(AssignmentFileDto assignmentFileDto) throws Exception {
        return assignmentFileRepository.save(convertToEntity(assignmentFileDto));
    }

    public AssignmentFile getAssignmentFile(Long id) {
        Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public AssignmentFile updateAssignmentFile(Long id, AssignmentFileDto assignmentFileDto) throws Exception {
        Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(id);
        AssignmentFile assignmentFile = fileEntityWrapper.get();
        assignmentFile.setType(assignmentFileDto.getType());

        Optional<User> userEntityWrapper = userRepository.findById(assignmentFileDto.getUserId());
        assignmentFile.setUser(userEntityWrapper.get());

        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(assignmentFileDto.getAssignmentId());
        assignmentFile.setAssignment(assignmentEntityWrapper.get());

        assignmentFile.setBase64Str(assignmentFileDto.getBase64Str());

        assignmentFileRepository.save(assignmentFile);
        return assignmentFile;
    }

    public void deleteAssignmentFile(Long id) {
        assignmentFileRepository.deleteById(id);
    }

    private AssignmentFile convertToEntity(AssignmentFileDto assignmentFileDto) {
        Optional<User> userEntityWrapper = userRepository.findById(assignmentFileDto.getUserId());
        Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(assignmentFileDto.getAssignmentId());

        return AssignmentFile.builder()
                .type(assignmentFileDto.getType())
                .user(userEntityWrapper.get())
                .assignment(assignmentEntityWrapper.get())
                .base64Str(assignmentFileDto.getBase64Str())
                .build();
    }
}
