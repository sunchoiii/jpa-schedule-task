package com.sparta.jpascheduletask.controller;

import com.sparta.jpascheduletask.dto.ScheduleReponseDto;
import com.sparta.jpascheduletask.dto.ScheduleRequestDto;
import com.sparta.jpascheduletask.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    // 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일  포함(JPA의 지연 로딩 기능)
    @GetMapping("/schedule/{schedule_id}")
    public ScheduleReponseDto findByIdPlusUser(@PathVariable Long schedule_id) {
        return scheduleService.findByIdPlusUser(schedule_id);
    }

    // 일정을 Spring Data JPA의 Pageable과 Page 인터페이스를 활용하여 페이지네이션을 구현
    @GetMapping("/schedule/list")
    public Page<ScheduleReponseDto> findScheduleList(@PageableDefault(size = 10, sort = "updateDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return scheduleService.findScheduleList(pageable);
    }


    // 일정 수정 (아이디로 조회 후 작성자명, 제목, 내용 수정)
    @PutMapping("/schedule/{schedule_id}")
    public ScheduleReponseDto updateSchedule(@PathVariable Long schedule_id, @RequestBody ScheduleRequestDto requestDto) {
        return scheduleService.update(schedule_id, requestDto);
    }

    // 일정 삭제 시 댓글도 함께 삭제
    @DeleteMapping("/schedule/{schedule_id}")
    public String deleteSchedule(@PathVariable Long schedule_id) {
        return scheduleService.deleteSchedule(schedule_id);
    }

}
