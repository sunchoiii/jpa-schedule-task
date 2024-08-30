package com.sparta.jpascheduletask.service;

import com.sparta.jpascheduletask.dto.UserScheduleRequestDto;
import com.sparta.jpascheduletask.entity.Schedule;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.entity.UserSchedule;
import com.sparta.jpascheduletask.repository.ScheduleRepository;
import com.sparta.jpascheduletask.repository.UserRepository;
import com.sparta.jpascheduletask.repository.UserScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class UserScheduleService {
    private final UserScheduleRepository userScheduleRepository;
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;


    //일정을 작성한 유저는 추가로 일정 담당 유저들을 배치할 수 있습니다.
    @Transactional
    public void manageUser(Long schedule_id, UserScheduleRequestDto requestDto) {
        // 해당 일정이 있는지 조회
        Schedule schedule = scheduleRepository.findById(schedule_id).orElseThrow(() ->
                new IllegalArgumentException("선택한 일정은 존재하지 않습니다.")
        );

        // 일정 작성자 유저
        User scheduleUser = userRepository.findById(requestDto.getScheduleUserId())
                .orElseThrow(() -> new NullPointerException("작성 유저가 존재하지 않습니다."));

        // 일정 작성자가 배치하는 유저
        User managerUser = userRepository.findById(requestDto.getManagerUserId())
                .orElseThrow(() -> new NullPointerException("담당 유저가 존재하지 않습니다."));

        System.out.println(requestDto.getScheduleUserId() + " / " + requestDto.getManagerUserId());


        // 작성한 유저가 일치하는지 확인
        UserSchedule existingUserSchedule = userScheduleRepository.findByScheduleAndUser(schedule, scheduleUser);
        if (existingUserSchedule == null || !ObjectUtils.nullSafeEquals(scheduleUser.getUser_id(), existingUserSchedule.getUser().getUser_id())) {
            throw new IllegalArgumentException("작성자가 일치하지 않습니다.");
        }

        UserSchedule newManager = new UserSchedule(managerUser, schedule);
        userScheduleRepository.save(newManager);
    }
}
