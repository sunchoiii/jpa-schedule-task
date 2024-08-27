package com.sparta.jpascheduletask.user.service;

import com.sparta.jpascheduletask.user.dto.UserRequestDto;
import com.sparta.jpascheduletask.user.dto.UserResponseDto;
import com.sparta.jpascheduletask.user.entity.User;
import com.sparta.jpascheduletask.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto create(UserRequestDto requestDto) {
        User user = new User(requestDto);
        userRepository.save(user);
        UserResponseDto responseDto = new UserResponseDto(user);
        return responseDto;
    }

    public User findById(Long user_id) {
        return userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("해당하는 아이디의 유저가 없습니다."));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    @Transactional
    public UserResponseDto update(Long user_id, UserRequestDto requestDto) {
        User user = findById(user_id);
        user.update(requestDto);
        UserResponseDto userResponseDto = new UserResponseDto(user);
        return userResponseDto;
    }

    public String delete(Long user_id) {
        User user = findById(user_id);
        userRepository.delete(user);
        return "해당 아이디의 유저를 삭제하였습니다.";
    }
}
