package com.manager.homework.controller.api;

import com.manager.homework.service.MemberService;
import com.manager.homework.vo.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"1. Member"})
@RequestMapping("/v1/api")
public class MemberControllerV1 {
    private MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입")
    @PostMapping("/user/signup")
    public String execSignup(MemberDto memberDto) {
        memberService.joinUser(memberDto);

        return "redirect:/user/login";
    }

    @ApiOperation(value = "내정보", notes = "내정보")
    @GetMapping("/user/info")
    public String dispMyInfo() {
        return "/member/myinfo";
    }

}
