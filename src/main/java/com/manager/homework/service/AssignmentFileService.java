package com.manager.homework.service;

import com.google.common.collect.Lists;
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

    public List<AssignmentFile> createAssignmentFile(List<AssignmentFileDto> assignmentFileDtoList) {
        List<AssignmentFile> assignmentFileList = assignmentFileRepository.saveAll(convertToEntity(assignmentFileDtoList));
        return assignmentFileList;
    }

    public AssignmentFile getAssignmentFile(Long id) {
        Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public List<AssignmentFile> updateAssignmentFileList(List<AssignmentFileDto> assignmentFileDtoList) {
        List<AssignmentFile> assignmentFileList = Lists.newArrayList();

        for(AssignmentFileDto assignmentFileDto: assignmentFileDtoList){
            Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(assignmentFileDto.getAssignmentFileId());
            AssignmentFile assignmentFile = fileEntityWrapper.get();
            assignmentFile.setType(assignmentFileDto.getType());

            if (assignmentFileDto.getUserId() != null) {
                Optional<User> userEntityWrapper = userRepository.findById(assignmentFileDto.getUserId());
                assignmentFile.setUser(userEntityWrapper.get());
            }

            if (assignmentFileDto.getAssignmentId() != null) {
                Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(assignmentFileDto.getAssignmentId());
                assignmentFile.setAssignment(assignmentEntityWrapper.get());
            }

            if (assignmentFileDto.getFileString() != null) {
                assignmentFile.setFileString(assignmentFileDto.getFileString());
            }

            if (assignmentFileDto.getHistoryFileString() != null) {
                assignmentFile.setHistoryFileString(assignmentFileDto.getHistoryFileString());
            }
            assignmentFileList.add(assignmentFile);
        }

        assignmentFileRepository.saveAll(assignmentFileList);
        return assignmentFileList;
    }

    public void deleteAssignmentFile(Long id) {
        assignmentFileRepository.deleteById(id);
    }

    private List<AssignmentFile> convertToEntity(List<AssignmentFileDto> assignmentFileDtoList) {
        List<AssignmentFile> assignmentFileList = Lists.newArrayList();
        for (AssignmentFileDto assignmentFileDto : assignmentFileDtoList) {
            Optional<User> userEntityWrapper = userRepository.findById(assignmentFileDto.getUserId());
            Optional<Assignment> assignmentEntityWrapper = assignmentRepository.findById(assignmentFileDto.getAssignmentId());

            AssignmentFile assignmentFile = AssignmentFile.builder()
                    .type(assignmentFileDto.getType())
                    .user(userEntityWrapper.get())
                    .assignment(assignmentEntityWrapper.get())
                    .fileString(assignmentFileDto.getFileString())
                    .historyFileString(assignmentFileDto.getHistoryFileString())
                    .build();

            assignmentFileList.add(assignmentFile);
        }
        return assignmentFileList;
    }
}
