package com.manager.homework.service;

import com.manager.homework.domain.Notice;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.NoticeRepository;
import com.manager.homework.repository.NoticeRepositorySupport;
import com.manager.homework.repository.SubjectRepository;
import com.manager.homework.repository.UserRepository;
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

    public List<Notice> getNoticeList(NoticeDto noticeDto) {
        return noticeRepositorySupport.findByCondition(noticeDto);
    }

    public void createNotice(NoticeDto noticeDto) throws Exception {
        noticeRepository.save(convertToEntity(noticeDto));
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

    private Notice convertToEntity(NoticeDto noticeDto) {
        Optional<User> userEntityWrapper = userRepository.findById(noticeDto.getUserId());
        Optional<Subject> subjectEntityWrapper = subjectRepository.findById(noticeDto.getSubjectId());

        return Notice.builder()
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .build();
    }
}
