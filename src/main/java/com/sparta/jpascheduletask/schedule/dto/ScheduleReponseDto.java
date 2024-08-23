package com.sparta.jpascheduletask.schedule.dto;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleReponseDto {
    private Long schedule_id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


    public ScheduleReponseDto(Schedule schedule) {
        this.schedule_id = schedule.getSchedule_id();
        this.username = schedule.getUsername();
        this.title = schedule.getTitle();;
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }
}
