package com.manager.homework.controller;

import com.manager.homework.vo.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO, HttpServletRequest request) throws Exception {
        log.info("Request parameter : {}", userDTO);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test(@RequestParam String str, HttpServletRequest request) throws Exception {
        log.info("Request parameter : {}", str);



        return ResponseEntity.ok(str);
    }
}
