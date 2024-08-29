package com.sparta.jpascheduletask.dto;

import com.sparta.jpascheduletask.entity.UserRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username;
    private String email;
    private String password;
    private boolean admin = false;
    private String adminToken = "";
}
