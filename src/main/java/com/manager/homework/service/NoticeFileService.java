package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Notice;
import com.manager.homework.domain.NoticeFile;
import com.manager.homework.domain.User;
import com.manager.homework.repository.NoticeFileRepository;
import com.manager.homework.repository.NoticeFileRepositorySupport;
import com.manager.homework.repository.NoticeRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.vo.NoticeFileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeFileService {
    private final NoticeFileRepository noticeFileRepository;
    private final NoticeFileRepositorySupport noticeFileRepositorySupport;
    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;

    public List<NoticeFile> getNoticeFileList(NoticeFileDto noticeFileDto) {
        return noticeFileRepositorySupport.findByCondition(noticeFileDto);
    }

    public List<NoticeFile> createNoticeFile(List<NoticeFileDto> noticeFileDtoList) {
        return noticeFileRepository.saveAll(convertToEntity(noticeFileDtoList));
    }

    public NoticeFile getNoticeFile(Long id) {
        Optional<NoticeFile> fileEntityWrapper = noticeFileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public NoticeFile updateNoticeFile(Long id, NoticeFileDto noticeFileDto) {
        Optional<NoticeFile> fileEntityWrapper = noticeFileRepository.findById(id);
        NoticeFile noticeFile = fileEntityWrapper.get();

        Optional<User> userEntityWrapper = userRepository.findById(noticeFileDto.getUserId());
        noticeFile.setUser(userEntityWrapper.get());

        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(noticeFileDto.getNoticeId());
        noticeFile.setNotice(noticeEntityWrapper.get());

        noticeFile.setFileString(noticeFileDto.getFileString());

        noticeFileRepository.save(noticeFile);
        return noticeFile;
    }

    public void deleteNoticeFile(Long id) {
        noticeFileRepository.deleteById(id);
    }

    private List<NoticeFile> convertToEntity(List<NoticeFileDto> noticeFileDtoList) {
        List<NoticeFile> noticeFileList = Lists.newArrayList();
        for (NoticeFileDto noticeFileDto : noticeFileDtoList) {
            Optional<User> userEntityWrapper = userRepository.findById(noticeFileDto.getUserId());
            Optional<Notice> noticeEntityWrapper = noticeRepository.findById(noticeFileDto.getNoticeId());

            NoticeFile noticeFile = NoticeFile.builder()
                    .user(userEntityWrapper.get())
                    .notice(noticeEntityWrapper.get())
                    .fileString(noticeFileDto.getFileString())
                    .build();

            noticeFileList.add(noticeFile);
        }

        return noticeFileList;
    }
}
