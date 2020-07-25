package com.manager.homework.controller.api;

import com.manager.homework.common.Response;
import com.manager.homework.domain.User;
import com.manager.homework.service.UserService;
import com.manager.homework.vo.LoginRequestDto;
import com.manager.homework.vo.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Api(tags = {"1. User"})
@AllArgsConstructor
public class UserApiController {
    private UserService userService;

    @ApiOperation(value = "전체 사용자 리스트 조회", notes = "전체 사용자 리스트 조회")
    @GetMapping("/users")
    public Response<List<User>> getAllUserList(UserDto userDto) {
        return Response.ok(userService.getUserList(userDto));
    }

    @ApiOperation(value = "사용자 생성", notes = "회원가입")
    @PostMapping("/users")
    public Response<String> createUser(@RequestBody UserDto userDto) throws Exception {
        userService.createUser(userDto);
        return Response.ok();
    }

    @ApiOperation(value = "사용자 상세 조회", notes = "특정 사용자 상세 정보 조회")
    @GetMapping("/users/{id}")
    public Response<User> getUser(@PathVariable("id") Long id) {
        return Response.ok(userService.getUser(id));
    }

    @ApiOperation(value = "사용자 정보 수정", notes = "특정 사용자 정보 수정")
    @PutMapping("/users/{id}")
    public Response<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) throws Exception {
        User user = userService.updateUser(id, userDto);
        return Response.ok(user);
    }

    @ApiOperation(value = "사용자 삭제", notes = "특정 사용자 삭제")
    @DeleteMapping("/users/{id}")
    public Response<String> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return Response.ok();
    }

    @ApiOperation(value = "로그인", notes = "로그인")
    @PostMapping("/login")
    public Response<User> loginUser(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userService.loginUser(email, password);
        return Response.ok(user);
    }
}
