package com.manager.homework.service;

import com.manager.homework.domain.*;
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

    public NoticeFile createNoticeFile(NoticeFileDto noticeFileDto) throws Exception {
        return noticeFileRepository.save(convertToEntity(noticeFileDto));
    }

    public NoticeFile getNoticeFile(Long id) {
        Optional<NoticeFile> fileEntityWrapper = noticeFileRepository.findById(id);
        return fileEntityWrapper.get();
    }

    public NoticeFile updateNoticeFile(Long id, NoticeFileDto noticeFileDto) throws Exception {
        Optional<NoticeFile> fileEntityWrapper = noticeFileRepository.findById(id);
        NoticeFile noticeFile = fileEntityWrapper.get();

        Optional<User> userEntityWrapper = userRepository.findById(noticeFileDto.getUserId());
        noticeFile.setUser(userEntityWrapper.get());

        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(noticeFileDto.getNoticeId());
        noticeFile.setNotice(noticeEntityWrapper.get());

        noticeFile.setBase64Str(noticeFileDto.getBase64Str());

        noticeFileRepository.save(noticeFile);
        return noticeFile;
    }

    public void deleteNoticeFile(Long id) {
        noticeFileRepository.deleteById(id);
    }

    private NoticeFile convertToEntity(NoticeFileDto noticeFileDto) {
        Optional<User> userEntityWrapper = userRepository.findById(noticeFileDto.getUserId());
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(noticeFileDto.getNoticeId());

        return NoticeFile.builder()
                .user(userEntityWrapper.get())
                .notice(noticeEntityWrapper.get())
                .base64Str(noticeFileDto.getBase64Str())
                .build();
    }
}
