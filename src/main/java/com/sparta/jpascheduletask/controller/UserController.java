package com.sparta.jpascheduletask.controller;

import com.sparta.jpascheduletask.dto.UserRequestDto;
import com.sparta.jpascheduletask.dto.UserResponseDto;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    public UserResponseDto signup(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        return userService.signup(requestDto, res);
    }

    //유저 단건 조회
    @GetMapping("/user/{user_id}")
    public User findById(@PathVariable Long user_id) {
        return userService.findById(user_id);
    }

    // 유저 전체 조회
    @GetMapping("/user")
    public List<UserResponseDto> findAll() {
        return userService.findAll();
    }

    // 유저 수정
    @PutMapping("/user/{user_id}")
    public UserResponseDto updateUser(@PathVariable Long user_id, @RequestBody UserRequestDto requestDto) {
        return userService.update(user_id, requestDto);
    }

    // 유저 삭제
    @DeleteMapping("/user/{user_id}")
    public String deleteUser (@PathVariable Long user_id) {
        return userService.delete(user_id);
    }

}
