package com.manager.homework.service;

import com.google.common.collect.Lists;
import com.manager.homework.domain.AssignmentFile;
import com.manager.homework.domain.WrongAnswerNote;
import com.manager.homework.repository.WrongAnswerNoteRepository;
import com.manager.homework.vo.WrongAnswerNoteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WrongAnswerNoteService {
    private final WrongAnswerNoteRepository wrongAnswerNoteRepository;

    public List<WrongAnswerNoteResponse> getWrongAnswerNoteList(Long userId) {
        List<WrongAnswerNoteResponse> result = Lists.newArrayList();
        List<WrongAnswerNote> wrongAnswerNoteList = wrongAnswerNoteRepository.findByUserId(userId);
        for (WrongAnswerNote wrongAnswerNote: wrongAnswerNoteList) {
            result.add(convertToResponse(wrongAnswerNote));
        }
        return result;
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
