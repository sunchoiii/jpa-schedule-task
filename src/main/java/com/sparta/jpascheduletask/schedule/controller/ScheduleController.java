package com.sparta.jpascheduletask.schedule.controller;

import com.sparta.jpascheduletask.schedule.dto.ScheduleReponseDto;
import com.sparta.jpascheduletask.schedule.dto.ScheduleRequestDto;
import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 일정 등록
    @PostMapping("/schedule")
    public ScheduleReponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.createSchedule(requestDto);
    }

    // 일정 단건 조회 (일정 아이디로 조회)
    @GetMapping("/schedule/{schedule_id}")
    public Schedule findById(@PathVariable Long schedule_id) {
        return scheduleService.findById(schedule_id);
    }

    // 일정 수정 (아이디로 조회 후 작성자명, 제목, 내용 수정)
    @PutMapping("/schedule/{schedule_id}")
    public ScheduleReponseDto updateSchedule(@PathVariable Long schedule_id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.update(schedule_id, requestDto);
    }

}
