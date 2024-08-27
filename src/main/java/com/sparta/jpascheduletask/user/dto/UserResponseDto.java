package com.sparta.jpascheduletask.user.dto;

import com.sparta.jpascheduletask.user.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
    private Long user_id;
    private String username;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public UserResponseDto(User user) {
        this.user_id = user.getUser_id();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createDate = user.getCreateDate();
        this.updateDate = user.getUpdateDate();

    }
}
