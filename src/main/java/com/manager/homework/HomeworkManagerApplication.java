package com.manager.homework;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class HomeworkManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HomeworkManagerApplication.class, args);
    }
}
