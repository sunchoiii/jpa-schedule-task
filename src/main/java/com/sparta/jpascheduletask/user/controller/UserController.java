package com.sparta.jpascheduletask.user.controller;

import com.sparta.jpascheduletask.user.dto.UserRequestDto;
import com.sparta.jpascheduletask.user.dto.UserResponseDto;
import com.sparta.jpascheduletask.user.entity.User;
import com.sparta.jpascheduletask.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 유저 저장
    @PostMapping("/user")
    public UserResponseDto createUser(@RequestBody UserRequestDto requestDto) {
        return userService.create(requestDto);
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
