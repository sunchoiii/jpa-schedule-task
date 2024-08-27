package com.sparta.jpascheduletask.user.dto;

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
