package com.manager.homework.service;

import com.manager.homework.domain.File;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.FileRepository;
import com.manager.homework.repository.FileRepositorySupport;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.vo.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FileRepositorySupport fileRepositorySupport;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;

    public List<File> getFileList(FileDto fileDto) {
        return fileRepositorySupport.findByCondition(fileDto);
    }

    public void createFile(FileDto fileDto) throws Exception {
        fileRepository.save(convertToEntity(fileDto));
    }

    public File getFile(Long id) {
        Optional<File> fileEntityWrapper = fileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public File updateFile(Long id, FileDto fileDto) throws Exception {
        Optional<File> fileEntityWrapper = fileRepository.findById(id);
        File file = fileEntityWrapper.get();
        file.setType(fileDto.getType());

        Optional<User> userEntityWrapper = userRepository.findById(fileDto.getUserId());
        file.setUser(userEntityWrapper.get());

        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(fileDto.getSubjectId());
        file.setSubject(subjectEntityWrapper.get());

        file.setBase64Str(fileDto.getBase64Str());

        fileRepository.save(file);
        return file;
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    private File convertToEntity(FileDto fileDto) {
        Optional<User> userEntityWrapper = userRepository.findById(fileDto.getUserId());
        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(fileDto.getSubjectId());

        return File.builder()
                .type(fileDto.getType())
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .base64Str(fileDto.getBase64Str())
                .build();
    }
}
