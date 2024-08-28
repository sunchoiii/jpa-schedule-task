package com.sparta.jpascheduletask.service;

import com.sparta.jpascheduletask.config.PasswordEncoder;
import com.sparta.jpascheduletask.dto.UserRequestDto;
import com.sparta.jpascheduletask.dto.UserResponseDto;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.repository.UserRepository;
import com.sparta.jpascheduletask.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserScheduleRepository userScheduleRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public UserResponseDto signup(UserRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        User user = new User(username, password, email);
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
