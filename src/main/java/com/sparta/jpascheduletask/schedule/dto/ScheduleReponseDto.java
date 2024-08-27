package com.sparta.jpascheduletask.schedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ScheduleReponseDto {
    private Long schedule_id;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int commentCount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<UserDto> users;

    public ScheduleReponseDto(Schedule schedule) {
        this.schedule_id = schedule.getSchedule_id();
        this.title = schedule.getTitle();;
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
        this.commentCount = schedule.getComments().size(); //댓글 개수 설정
    }

    public ScheduleReponseDto(Schedule schedule, List<UserDto> users) {
        this.schedule_id = schedule.getSchedule_id();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
        this.commentCount = schedule.getComments().size(); //댓글 개수 설정
        this.users = users;
    }
}
