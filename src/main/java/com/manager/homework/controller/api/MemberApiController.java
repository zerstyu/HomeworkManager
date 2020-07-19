package com.manager.homework.controller.api;

import com.manager.homework.domain.Member;
import com.manager.homework.service.MemberService;
import com.manager.homework.vo.CommonResponse;
import com.manager.homework.vo.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"1. Member"})
@AllArgsConstructor
public class MemberApiController {
    public static final Logger logger = LoggerFactory.getLogger(MemberApiController.class);

    private MemberService memberService;

    @ApiOperation(value = "전체 사용자 리스트 조회", notes = "전체 사용자 리스트 조회")
    @GetMapping("/members")
    public ResponseEntity getAllMemberList() {
        try {
            List<Member> memberList = memberService.getAllMemberList();
            return new ResponseEntity(new CommonResponse("SUCCESS", memberList), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "사용자 생성", notes = "사용자 생성")
    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody MemberDto memberDto) {
        try {
            memberService.joinUser(memberDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", "정상적으로 생성 되었습니다"), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "사용자 상세조회", notes = "특정 사용자 상세 정보 조회")
    @GetMapping("/members/{email}")
    public ResponseEntity getMember(@PathVariable("email") String email) {
        try {
            UserDetails member = memberService.loadUserByUsername(email);
            return new ResponseEntity(new CommonResponse("SUCCESS", member), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "특정 사용자 정보 수정")
    @PutMapping("/members/{email}")
    public ResponseEntity updateMember(@PathVariable("email") String email, @RequestBody MemberDto memberDto) {
        try {
            Member member = memberService.updateUserByUsername(email, memberDto);
            return new ResponseEntity(new CommonResponse("SUCCESS", member), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "사용자 삭제", notes = "특정 사용자 삭제")
    @DeleteMapping("/members/{email}")
    public ResponseEntity deleteMember(@PathVariable("email") String email, @RequestBody MemberDto memberDto) {
        try {
            memberService.deleteUserByUsername(email);
            return new ResponseEntity(new CommonResponse("SUCCESS", "정상적으로 삭제 되었습니다"), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseEntity(new CommonResponse("ERROR", e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
