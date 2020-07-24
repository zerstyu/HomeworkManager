package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.File;
import com.manager.homework.service.FileService;
import com.manager.homework.vo.FileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"3. File"})
@AllArgsConstructor
public class FileApiController {
    private final FileService fileService;

    @ApiOperation(value = "파일 리스트 조회", notes = "파일 리스트 조회")
    @GetMapping("/files")
    public Response<List<File>> getFileList(FileDto fileDto) {
        return Response.ok(fileService.getFileList(fileDto));
    }

    @ApiOperation(value = "파일 생성", notes = "파일 생성")
    @PostMapping("/files")
    public Response<String> createFile(@RequestBody FileDto fileDto) throws Exception {
        fileService.createFile(fileDto);
        return Response.ok();
    }

    @ApiOperation(value = "파일 상세 정보 조회", notes = "파일 상세 정보 조회")
    @GetMapping("/files/{id}")
    public Response<File> getFile(@PathVariable("id") Long id) {
        return Response.ok(fileService.getFile(id));
    }

    @ApiOperation(value = "파일 정보 수정", notes = "파일 정보 수정")
    @PutMapping("/files/{id}")
    public Response<File> updateFile(@PathVariable("id") Long id, @RequestBody FileDto fileDto) throws Exception {
        File file = fileService.updateFile(id, fileDto);
        return Response.ok(file);
    }

    @ApiOperation(value = "파일 삭제", notes = "파일 사용자 삭제")
    @DeleteMapping("/files/{id}")
    public Response<String> deleteFile(@PathVariable("id") Long id) {
        fileService.deleteFile(id);
        return Response.ok();
    }
}
