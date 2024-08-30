package com.sparta.jpascheduletask.controller;

import com.sparta.jpascheduletask.dto.LoginRequestDto;
import com.sparta.jpascheduletask.dto.UserRequestDto;
import com.sparta.jpascheduletask.dto.UserResponseDto;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto, HttpServletResponse res) {
        return ResponseEntity.ok(userService.signup(requestDto, res));
    }

    //로그인
    @PostMapping("/user/login")
    public ResponseEntity<UserResponseDto> login (@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
        return ResponseEntity.ok(userService.login(requestDto, res));
    }

    //유저 단건 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long user_id) {
        return ResponseEntity.ok(userService.findById(user_id));
    }

    // 유저 전체 조회
    @GetMapping("/user/list")
    public ResponseEntity<List<UserResponseDto>> findUserList() {
        return ResponseEntity.ok(userService.findUserList());
    }

    // 유저 수정
    @PutMapping("/user/{user_id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long user_id, @RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.update(user_id, requestDto));
    }

    // 유저 삭제
    @DeleteMapping("/user/{user_id}")
    public String deleteUser (@PathVariable Long user_id) {
        return userService.delete(user_id);
    }

}
