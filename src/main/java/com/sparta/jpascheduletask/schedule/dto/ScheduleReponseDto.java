package com.sparta.jpascheduletask.schedule.dto;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleReponseDto {
    private Long schedule_id;
    private String title;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int commentCount;

    // 정렬 항목 : 할일 제목, 할일 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드를 조회
    public ScheduleReponseDto(Schedule schedule) {
        this.schedule_id = schedule.getSchedule_id();
        this.title = schedule.getTitle();;
        this.contents = schedule.getContents();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
        this.commentCount = schedule.getComments().size(); //댓글 개수 설정
    }
}
