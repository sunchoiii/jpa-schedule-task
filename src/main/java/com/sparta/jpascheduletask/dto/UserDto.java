package com.sparta.jpascheduletask.dto;
// 6단계 일정 단건 조회 시 유저 정보 중 3가지만 불러올때
import lombok.Getter;

@Getter
public class UserDto {
    private Long user_id;
    private String username;
    private String email;

    public UserDto(Long userId, String username, String email) {
        this.user_id = userId;
        this.username = username;
        this.email = email;

    }
}
