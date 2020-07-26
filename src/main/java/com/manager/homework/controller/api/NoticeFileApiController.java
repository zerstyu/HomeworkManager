package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.NoticeFile;
import com.manager.homework.service.NoticeFileService;
import com.manager.homework.vo.NoticeFileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"6. NoticeFile"})
@AllArgsConstructor
public class NoticeFileApiController {
    private final NoticeFileService noticeFileService;

    @ApiOperation(value = "공지 파일 리스트 조회", notes = "공지 파일 리스트 조회")
    @GetMapping("/notice_files")
    public Response<List<NoticeFile>> getNoticeFileList(NoticeFileDto noticeFileDto) {
        return Response.ok(noticeFileService.getNoticeFileList(noticeFileDto));
    }

    @ApiOperation(value = "공지 파일 생성", notes = "공지 파일 생성")
    @PostMapping("/notice_files")
    public Response<List<NoticeFile>> createNoticeFile(@RequestBody List<NoticeFileDto> noticeFileDtoList) throws Exception {
        return Response.ok(noticeFileService.createNoticeFile(noticeFileDtoList));
    }

    @ApiOperation(value = "공지 파일 상세 정보 조회", notes = "공지 파일 상세 정보 조회")
    @GetMapping("/notice_files/{id}")
    public Response<NoticeFile> getNoticeFile(@PathVariable("id") Long id) {
        return Response.ok(noticeFileService.getNoticeFile(id));
    }

    @ApiOperation(value = "공지 파일 정보 수정", notes = "공지 파일 정보 수정")
    @PutMapping("/notice_files/{id}")
    public Response<NoticeFile> updateNoticeFile(@PathVariable("id") Long id, @RequestBody NoticeFileDto noticeFileDto) throws Exception {
        NoticeFile noticeFile = noticeFileService.updateNoticeFile(id, noticeFileDto);
        return Response.ok(noticeFile);
    }

    @ApiOperation(value = "공지 파일 삭제", notes = "공지 파일 사용자 삭제")
    @DeleteMapping("/notice_files/{id}")
    public Response<String> deleteNoticeFile(@PathVariable("id") Long id) {
        noticeFileService.deleteNoticeFile(id);
        return Response.ok();
    }
}
