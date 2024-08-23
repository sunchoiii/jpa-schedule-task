package com.sparta.jpascheduletask.schedule.service;

import com.sparta.jpascheduletask.schedule.dto.ScheduleReponseDto;
import com.sparta.jpascheduletask.schedule.dto.ScheduleRequestDto;
import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //일정 등록
    public ScheduleReponseDto createSchedule(ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);
        // DB 저장
        Schedule saveSchedule = scheduleRepository.save(schedule);
        // Entity -> ResponseDto
        ScheduleReponseDto scheduleReponseDto = new ScheduleReponseDto(schedule);
        return scheduleReponseDto;
    }

    // 일정 단건 조회
    public Schedule findById(Long schedule_id) {
        return scheduleRepository.findById(String.valueOf(schedule_id)).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
    }

    // 일정 수정
    @Transactional
    public ScheduleReponseDto update(Long schedule_id, ScheduleRequestDto requestDto) {
        // 입력한 ID의 일정이 있는지 조회
        Schedule schedule = findById(schedule_id);
        // schedule 객체에 만든 update 메서드 실행
        schedule.update(requestDto);
        // scheduleReponseDto 에 담아서 변환
        ScheduleReponseDto scheduleReponseDto = new ScheduleReponseDto(schedule);
        return scheduleReponseDto;

    }





}
