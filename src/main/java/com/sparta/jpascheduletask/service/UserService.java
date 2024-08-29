package com.sparta.jpascheduletask.service;

import com.sparta.jpascheduletask.config.PasswordEncoder;
import com.sparta.jpascheduletask.dto.LoginRequestDto;
import com.sparta.jpascheduletask.dto.UserRequestDto;
import com.sparta.jpascheduletask.dto.UserResponseDto;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.entity.UserRoleEnum;
import com.sparta.jpascheduletask.jwt.JwtUtil;
import com.sparta.jpascheduletask.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    //회원가입
    @Transactional
    public UserResponseDto signup(UserRequestDto requestDto, HttpServletResponse res) {
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

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);

        // JWT 생성 및 쿠키 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new UserResponseDto(user);
    }

    // 로그인
    @Transactional
    public UserResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        //사용자 확인 -> 일치하지 않으면 401 에러 반환
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일이 일치하지 않습니다.")
        );

        //비밀번호 확인 -> 일치하지 않으면 401 에러 반환
        if (! passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키 저장 후 Response 객체에 추가
        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
        jwtUtil.addJwtToCookie(token, res);

        return new UserResponseDto(user);
    }


    @Transactional(readOnly = true)
    public UserResponseDto findById(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("해당하는 아이디의 유저가 없습니다."));
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findUserList() {
        return userRepository.findAll().stream().map(UserResponseDto::new).toList();
    }

    @Transactional
    public UserResponseDto update(Long user_id, UserRequestDto requestDto) {
        User user = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("해당하는 아이디의 유저가 없습니다."));
        user.update(requestDto);
        return new UserResponseDto(user);
    }

    @Transactional
    public String delete(Long user_id) {
        User user = userRepository.findById(user_id).orElseThrow(() ->
                new IllegalArgumentException("해당하는 아이디의 유저가 없습니다."));
        userRepository.delete(user);
        return "해당 아이디의 유저를 삭제하였습니다.";
    }
}
