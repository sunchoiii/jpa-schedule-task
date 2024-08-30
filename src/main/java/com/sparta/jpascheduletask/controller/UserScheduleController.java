package com.sparta.jpascheduletask.controller;

import com.sparta.jpascheduletask.dto.UserScheduleRequestDto;
import com.sparta.jpascheduletask.service.UserScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserScheduleController {

    private final UserScheduleService userScheduleService;

    //일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다.
    @PostMapping("/api/schedule/{schedule_id}/managers")
    public void manageUser(@PathVariable Long schedule_id, @RequestBody UserScheduleRequestDto requestDto ) {
        userScheduleService.manageUser(schedule_id, requestDto);
    }

}