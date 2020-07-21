package com.manager.homework.controller.api;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Api(tags = {"3. File"})
@AllArgsConstructor
public class FileApiController {
    public static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);
}
