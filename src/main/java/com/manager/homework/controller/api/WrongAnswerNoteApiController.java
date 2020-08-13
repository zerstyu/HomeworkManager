package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.service.WrongAnswerNoteService;
import com.manager.homework.vo.WrongAnswerNoteResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"12. WrongAnswerNote"})
@AllArgsConstructor
public class WrongAnswerNoteApiController {
    private final WrongAnswerNoteService wrongAnswerNoteService;

    @ApiOperation(value = "오답노트 리스트 조회", notes = "사용자의 오답노트 리스트")
    @GetMapping("/wrong_answer_notes")
    public Response<List<WrongAnswerNoteResponse>> getWrongAnswerNoteList(Long userId) {
        return Response.ok(wrongAnswerNoteService.getWrongAnswerNoteList(userId));
    }
}
