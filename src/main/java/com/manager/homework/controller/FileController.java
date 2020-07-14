package com.manager.homework.controller;

import com.manager.homework.vo.FileDto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {
    public static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> upload(@RequestPart List<MultipartFile> files, @RequestParam String polygon) throws Exception {
        List<String> list = new ArrayList<>();
        for (MultipartFile file : files) {
            String originalFileName = file.getOriginalFilename();
            File dest = new File("C:/image/" + originalFileName);
            file.transferTo(dest);
            // TODO
        }
        return list;
    }

    @PostMapping("/upload")
    public ResponseEntity saveAllReservation(@RequestBody FileDto fileDto) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
