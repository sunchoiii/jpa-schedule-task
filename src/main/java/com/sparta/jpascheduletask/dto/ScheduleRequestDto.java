package com.sparta.jpascheduletask.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private Long user_id;
    private String title;
    private String contents;

}
