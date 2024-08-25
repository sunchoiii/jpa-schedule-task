package com.sparta.jpascheduletask.schedule.controller;

import com.sparta.jpascheduletask.schedule.dto.ScheduleReponseDto;
import com.sparta.jpascheduletask.schedule.dto.ScheduleRequestDto;
import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 일정을 Spring Data JPA의 Pageable과 Page 인터페이스를 활용하여 페이지네이션을 구현
    @GetMapping("/schedule/param")
    public Page<ScheduleReponseDto> findAll(@PageableDefault(size = 10, sort = "updateDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return scheduleService.findAll(pageable);
    }


    // 일정 수정 (아이디로 조회 후 작성자명, 제목, 내용 수정)
    @PutMapping("/schedule/{schedule_id}")
    public ScheduleReponseDto updateSchedule(@PathVariable Long schedule_id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.update(schedule_id, requestDto);
    }

}
