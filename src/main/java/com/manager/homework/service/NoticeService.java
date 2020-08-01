package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.Notice;
import com.manager.homework.domain.NoticeFile;
import com.manager.homework.domain.Subject;
import com.manager.homework.domain.User;
import com.manager.homework.repository.*;
import com.manager.homework.vo.NoticeDto;
import com.manager.homework.vo.NoticeFileResponse;
import com.manager.homework.vo.NoticeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
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

    public List<NoticeResponse> getNoticeList(NoticeDto noticeDto) {
        List<NoticeResponse> noticeResponseList = Lists.newArrayList();
        List<Notice> noticeList = noticeRepositorySupport.findByCondition(noticeDto);
        for (Notice notice : noticeList) {
            noticeResponseList.add(convertToResponse(notice));
        }
        return noticeResponseList;
    }

    public Notice createNotice(NoticeDto noticeDto) {
        Notice notice = noticeRepository.save(convertToNoticeEntity(noticeDto));
        List<NoticeFile> noticeFileList = convertToEntity(notice, noticeDto.getNoticeFileStringList());
        noticeFileRepository.saveAll(noticeFileList);
        return notice;
    }

    public NoticeResponse getNotice(Long id) {
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(id);
        return convertToResponse(noticeEntityWrapper.get());
    }

    public Notice updateNotice(Long id, NoticeDto noticeDto) {
        Optional<Notice> noticeEntityWrapper = noticeRepository.findById(id);
        Notice notice = noticeEntityWrapper.get();

        if (noticeDto.getUserId() != null) {
            Optional<User> userEntityWrapper = userRepository.findById(noticeDto.getUserId());
            notice.setUser(userEntityWrapper.get());
        }

        if (noticeDto.getUserId() != null) {
            Optional<Subject> subjectEntityWrapper = subjectRepository.findById(noticeDto.getSubjectId());
            notice.setSubject(subjectEntityWrapper.get());
        }

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
                .type(noticeDto.getType())
                .user(userEntityWrapper.get())
                .subject(subjectEntityWrapper.get())
                .title(noticeDto.getTitle())
                .content(noticeDto.getContent())
                .expiredAt(noticeDto.getExpiredAt())
                .status(noticeDto.getStatus())
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

    private NoticeResponse convertToResponse(Notice notice) {
        NoticeResponse noticeResponse = new NoticeResponse();
        noticeResponse.setTitle(notice.getTitle());
        noticeResponse.setContent(notice.getContent());
        noticeResponse.setD_day(convertToDDay(notice.getExpiredAt()));
        noticeResponse.setType(notice.getType());
        noticeResponse.setStatus(notice.getStatus());
        noticeResponse.setExpiredAt(notice.getExpiredAt());
        noticeResponse.setUserId(notice.getUser().getId());
        noticeResponse.setUserName(notice.getUser().getName());
        List<NoticeFile> noticeFileList = noticeFileRepository.findByNoticeId(notice.getId());
        List<NoticeFileResponse> noticeFileResponseList = Lists.newArrayList();
        for (NoticeFile noticeFile : noticeFileList) {
            noticeFileResponseList.add(convertToResponse(noticeFile));
        }
        noticeResponse.setNoticeFileResponseList(noticeFileResponseList);
        return noticeResponse;
    }

    private NoticeFileResponse convertToResponse(NoticeFile noticeFile){
        NoticeFileResponse noticeFileResponse = new NoticeFileResponse();
        noticeFileResponse.setId(noticeFile.getId());
        noticeFileResponse.setFileString(noticeFile.getFileString());
        return noticeFileResponse;
    }

    private String convertToDDay(LocalDate date) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = date;

        Period period = Period.between(startDate, endDate);
        int dday = period.getDays();

        if (dday == 0) {
            return "D-DAY";
        } else if (dday > 0) {
            return "D-" + dday;
        } else {
            return "D+" + -dday;
        }
    }
}
