package com.sparta.jpascheduletask;

import com.sparta.jpascheduletask.entity.Schedule;
import com.sparta.jpascheduletask.entity.UserRoleEnum;
import com.sparta.jpascheduletask.repository.ScheduleRepository;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.repository.UserRepository;
import com.sparta.jpascheduletask.entity.UserSchedule;
import com.sparta.jpascheduletask.repository.UserScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@SpringBootTest
public class UserScheduleTest {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserScheduleRepository userScheduleRepository;


    @Test
    @Rollback(value = false)
    @Transactional
    @DisplayName("중간테이블 연관관계 테스트")
    void test4() {
        User user = new User();
        user.setUsername("선");
        user.setEmail("구글점컴");
        user.setPassword("qqq");
        user.setRole(UserRoleEnum.USER);

        User user2 = new User();
        user2.setUsername("체선");
        user2.setEmail("네이버점컴");
        user2.setPassword("qqq");
        user2.setRole(UserRoleEnum.USER);

        Schedule schedule = new Schedule();
        schedule.setTitle("과제");
        schedule.setContents("확인");

        Schedule schedule2 = new Schedule();
        schedule2.setTitle("어려움");
        schedule2.setContents("확인");

        // UserSchedule 객체 생성 및 연관관계 설정
        List<UserSchedule> userSchedules = new ArrayList<>();
        UserSchedule userSchedule1 = new UserSchedule();
        userSchedule1.setUser(user);
        userSchedule1.setSchedule(schedule);
        userSchedules.add(userSchedule1);

        UserSchedule userSchedule3 = new UserSchedule();
        userSchedule3.setUser(user2);
        userSchedule3.setSchedule(schedule);
        userSchedules.add(userSchedule3);

        userRepository.save(user);
        userRepository.save(user2);
        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule2);

        userScheduleRepository.saveAll(userSchedules);
    }


}
