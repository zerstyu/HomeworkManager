package com.manager.homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {

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
}
