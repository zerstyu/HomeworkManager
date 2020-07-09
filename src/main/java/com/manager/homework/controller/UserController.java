package com.manager.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    @PostMapping("/login")
    public ResponseEntity<String> receiveMoney(@RequestBody String str, HttpServletRequest request) throws Exception {
        log.info("Request parameter : {}", str);
        String copy = str + ".copy";
        return ResponseEntity.ok(copy);
    }
}
