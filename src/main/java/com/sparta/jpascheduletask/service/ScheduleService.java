package com.sparta.jpascheduletask.service;

import com.sparta.jpascheduletask.dto.ScheduleReponseDto;
import com.sparta.jpascheduletask.dto.ScheduleRequestDto;
import com.sparta.jpascheduletask.entity.Schedule;
import com.sparta.jpascheduletask.repository.ScheduleRepository;
import com.sparta.jpascheduletask.dto.UserDto;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.entity.UserSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    //일정 등록
    @Transactional
    public ScheduleReponseDto createSchedule(ScheduleRequestDto requestDto) {
        Schedule schedule = new Schedule(requestDto);
        scheduleRepository.save(schedule);
        return new ScheduleReponseDto(schedule);
    }

    // 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일  포함(JPA의 지연 로딩 기능)
    @Transactional(readOnly = true)
    public ScheduleReponseDto findByIdPlusUser(Long schedule_id) {
        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
        // 이 일정과 관련된 유저스케줄 테이블
        List<UserSchedule> userSchedules = schedule.getUserSchedules();
        // 유저 정보를 담을 리스트
        List<UserDto> users = new ArrayList<>();
        for (UserSchedule userSchedule : userSchedules) {
            User user = userSchedule.getUser();
            UserDto userDto = new UserDto(user.getUser_id(), user.getUsername(), user.getEmail());
            users.add(userDto);
        }
        return new ScheduleReponseDto(schedule, users);
    }

    //페이징 활용해서 페이지네이션 구현
    @Transactional(readOnly = true)
    public Page<ScheduleReponseDto> findScheduleList(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(ScheduleReponseDto::new);
    }

    // 일정 수정
    @Transactional
    public ScheduleReponseDto update(Long schedule_id, ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
        schedule.update(requestDto);
        return new ScheduleReponseDto(schedule);
    }


    // 일정 삭제 시 댓글도 함께 삭제
    @Transactional
    public String deleteSchedule(Long schedule_id) {
        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );
        scheduleRepository.delete(schedule);
        return "해당 일정이 삭제되었습니다.";
    }

}
