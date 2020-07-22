package com.manager.homework.service;

import com.manager.homework.domain.File;
import com.manager.homework.repository.FileRepositorySupport;
import com.manager.homework.vo.FileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepositorySupport fileRepositorySupport;

    public List<File> getFileList(FileDto fileDto) {
        List<File> queryDslList = fileRepositorySupport.findByCondition(fileDto);
        queryDslList.forEach(System.out::println);
        return queryDslList;
    }
}
