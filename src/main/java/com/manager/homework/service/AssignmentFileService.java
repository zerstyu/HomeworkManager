package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Assignment;
import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.domain.Block;
import com.manager.homework.domain.User;
import com.manager.homework.repository.*;
import com.manager.homework.vo.AssignmentFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private final BlockRepository blockRepository;

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int prefix = 4;

    public List<AssignmentFile> getAssignmentFileList(AssignmentFileDto assignmentFileDto) {
        return assignmentFileRepositorySupport.findByCondition(assignmentFileDto);
    }

    public List<AssignmentFile> createAssignmentFile(List<AssignmentFileDto> assignmentFileDtoList) {
        List<AssignmentFile> assignmentFileList = assignmentFileRepository.saveAll(convertToEntity(assignmentFileDtoList));
        for (AssignmentFile assignmentFile : assignmentFileList) {
            if (assignmentFile.getFileString() != null) {
                addBlock(assignmentFile);
            }
        }
        return assignmentFileList;
    }

    public AssignmentFile getAssignmentFile(Long id) {
        Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public AssignmentFile updateAssignmentFile(Long id, AssignmentFileDto assignmentFileDto) {
        Optional<AssignmentFile> fileEntityWrapper = assignmentFileRepository.findById(id);
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

        if (assignmentFile.getFileString() != null) {
            assignmentFile.setFileString(assignmentFileDto.getFileString());
            addBlock(assignmentFile);
        }

        assignmentFileRepository.save(assignmentFile);
        return assignmentFile;
    }

    private void addBlock(AssignmentFile assignmentFile) {
        Block block = new Block(assignmentFile.getFileString(), "0", new Date().getTime());
        block.mineBlock(prefix);
        blockchain.add(block);
        blockRepository.saveAll(blockchain);
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
                    .build();

            assignmentFileList.add(assignmentFile);
        }
        return assignmentFileList;
    }
}
