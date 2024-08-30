package com.sparta.jpascheduletask.dto;

import lombok.Getter;

@Getter
public class UserScheduleRequestDto {

    private Long scheduleUserId; // 일정 작성자 유저 id
    private Long managerUserId; // 일정 작성자가 배치하는 유저
}
