package com.manager.homework.controller.api;

import com.manager.homework.domain.File;
import com.manager.homework.service.FileService;
import com.manager.homework.vo.CommonResponse;
import com.manager.homework.vo.FileDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"3. File"})
@AllArgsConstructor
public class FileApiController {
    public static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    private final FileService fileService;

    @ApiOperation(value = "파일 리스트 조회", notes = "파일 리스트 조회")
    @GetMapping("/files")
    public ResponseEntity getFileList(FileDto fileDto) {
        try {
            List<File> fileList = fileService.getFileList(fileDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", fileList), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
