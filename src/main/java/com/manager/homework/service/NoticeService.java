package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Notice;
import com.manager.homework.domain.NoticeFile;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.*;
import com.manager.homework.vo.NoticeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final NoticeRepositorySupport noticeRepositorySupport;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final NoticeFileRepository noticeFileRepository;
    private final NoticeFileService noticeFileService;

    public List<Notice> getNoticeList(NoticeDto noticeDto) {
        return noticeRepositorySupport.findByCondition(noticeDto);
    }

    public Notice createNotice(NoticeDto noticeDto) throws Exception {
        Notice notice = noticeRepository.save(convertToNoticeEntity(noticeDto));
        List<NoticeFile> noticeFileList = convertToEntity(notice, noticeDto.getNoticeFileStringList());
        noticeFileRepository.saveAll(noticeFileList);
        return notice;
    }

    public Notice getNotice(Long id) {
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(id);
        return noticeEntityWrapper.get();
    }

    public Notice updateNotice(Long id, NoticeDto noticeDto) throws Exception {
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(id);
        Notice notice = noticeEntityWrapper.get();

        Optional<User> userEntityWrapper = userRepository.findById(noticeDto.getUserId());
        notice.setUser(userEntityWrapper.get());

        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(noticeDto.getSubjectId());
        notice.setSubject(subjectEntityWrapper.get());

        notice.setTitle(noticeDto.getTitle());
        notice.setContent(noticeDto.getContent());

        noticeRepository.save(notice);
        return notice;
    }

    public void deleteNotice(Long id) {
        noticeRepository.deleteById(id);
    }

    private Notice convertToNoticeEntity(NoticeDto noticeDto) {
        Optional<User> userEntityWrapper = userRepository.findById(noticeDto.getUserId());
        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(noticeDto.getSubjectId());

        return Notice.builder()
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .build();
    }

    private List<NoticeFile> convertToEntity(Notice notice, List<String> noticeFileStringList) {
        List<NoticeFile> noticeFileList = Lists.newArrayList();
        for (String fileString : noticeFileStringList) {
            NoticeFile noticeFile = NoticeFile.builder()
                    .user(notice.getUser())
                    .notice(notice)
                    .fileString(fileString)
                    .build();

            noticeFileList.add(noticeFile);
        }
        return noticeFileList;
    }
}
