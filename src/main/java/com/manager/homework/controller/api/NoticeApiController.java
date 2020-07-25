package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.Notice;
import com.manager.homework.service.NoticeService;
import com.manager.homework.vo.NoticeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"3. Notice"})
@AllArgsConstructor
public class NoticeApiController {
    private NoticeService noticeService;

    @ApiOperation(value = "과제 공지 리스트 조회", notes = "과제 공지 리스트 조회")
    @GetMapping("/notices")
    public Response<List<Notice>> getNoticeList(NoticeDto noticeDto) {
        return Response.ok(noticeService.getNoticeList(noticeDto));
    }

    @ApiOperation(value = "과제 공지 생성", notes = "과제 공지 생성")
    @PostMapping("/notices")
    public Response<String> createNotice(@RequestBody NoticeDto noticeDto) throws Exception {
        noticeService.createNotice(noticeDto);
        return Response.ok();
    }

    @ApiOperation(value = "과제 공지 상세 조회", notes = "과제 공지 상세 조회")
    @GetMapping("/notices/{id}")
    public Response<Notice> getNotice(@PathVariable("id") Long id) {
        return Response.ok(noticeService.getNotice(id));
    }

    @ApiOperation(value = "과제 공지 정보 수정", notes = "과제 공지 정보 수정")
    @PutMapping("/notices/{id}")
    public Response<Notice> updateNotice(@PathVariable("id") Long id, @RequestBody NoticeDto noticeDto) throws Exception {
        Notice notice = noticeService.updateNotice(id, noticeDto);
        return Response.ok(notice);
    }

    @ApiOperation(value = "과제 공지 삭제", notes = "과제 공지 삭제")
    @DeleteMapping("/notices/{id}")
    public Response<String> deleteNotice(@PathVariable("id") Long id) {
        noticeService.deleteNotice(id);
        return Response.ok();
    }
}
