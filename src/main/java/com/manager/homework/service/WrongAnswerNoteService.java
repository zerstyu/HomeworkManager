package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.domain.User;
import com.manager.homework.domain.WrongAnswerNote;
import com.manager.homework.repository.AssignmentFileRepository;
import com.manager.homework.repository.UserRepository;
import com.manager.homework.repository.WrongAnswerNoteRepository;
import com.manager.homework.vo.WrongAnswerNoteDto;
import com.manager.homework.vo.WrongAnswerNoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class WrongAnswerNoteService {
    private final WrongAnswerNoteRepository wrongAnswerNoteRepository;
    private final UserRepository userRepository;
    private final AssignmentFileRepository assignmentFileRepository;

    public List<WrongAnswerNoteResponse> getWrongAnswerNoteList(Long userId) {
        List<WrongAnswerNoteResponse> result = Lists.newArrayList();
        List<WrongAnswerNote> wrongAnswerNoteList = wrongAnswerNoteRepository.findByUserId(userId);
        for (WrongAnswerNote wrongAnswerNote: wrongAnswerNoteList) {
            result.add(convertToResponse(wrongAnswerNote));
        }
        return result;
    }

    public WrongAnswerNote createWrongAnswerNote(WrongAnswerNoteDto wrongAnswerNoteDto) {
        Optional<User> userEntityWrapper = userRepository.findById(wrongAnswerNoteDto.getUserId());
        Optional<AssignmentFile> assignmentFileWrapper = assignmentFileRepository.findById(wrongAnswerNoteDto.getAssignmentFileId());

        WrongAnswerNote wrongAnswerNote = WrongAnswerNote.builder()
                .user(userEntityWrapper.get())
                .assignmentFile(assignmentFileWrapper.get())
                .build();

        return wrongAnswerNoteRepository.save(wrongAnswerNote);
    }

    private WrongAnswerNoteResponse convertToResponse(WrongAnswerNote wrongAnswerNote) {
        AssignmentFile assignmentFile = wrongAnswerNote.getAssignmentFile();
        WrongAnswerNoteResponse wrongAnswerNoteResponse = WrongAnswerNoteResponse.builder()
                .assignmentFileId(assignmentFile.getId())
                .assignmentId(assignmentFile.getAssignment().getId())
                .subjectName(assignmentFile.getAssignment().getSubject().getName())
                .feedback(assignmentFile.getAssignment().getFeedback())
                .noticeTitle(assignmentFile.getAssignment().getNotice().getTitle())
                .score(assignmentFile.getAssignment().getScore())
                .build();
        return wrongAnswerNoteResponse;
    }
}
