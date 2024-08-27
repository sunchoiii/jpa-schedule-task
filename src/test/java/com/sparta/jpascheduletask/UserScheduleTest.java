package com.sparta.jpascheduletask;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.schedule.repository.ScheduleRepository;
import com.sparta.jpascheduletask.user.entity.User;
import com.sparta.jpascheduletask.user.repository.UserRepository;
import com.sparta.jpascheduletask.userschedule.UserSchedule;
import com.sparta.jpascheduletask.userschedule.UserScheduleRepository;
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
    @DisplayName("중간 테이블 테스트")
    void test1() {
        User user = new User();
        user.setUsername("sun");
        user.setEmail("google.com");

        Schedule schedule = new Schedule();
        schedule.setTitle("중간테이블");
        schedule.setContents("확인22");

        // 저장
        UserSchedule userSchedule = new UserSchedule();
        userSchedule.setUser(user);
        userSchedule.setSchedule(schedule);

        userRepository.save(user);
        scheduleRepository.save(schedule);
        userScheduleRepository.save(userSchedule);
    }

    @Test
    @DisplayName("중간 테이블 통해 조회")
    void test2() {
        // 1번 스케줄 조회
        UserSchedule userSchedule = userScheduleRepository.findById(1L).orElseThrow(NullPointerException::new);

        // 유저스케줄 객체 사용하여 유저정보 조회
        User user = userSchedule.getUser();
        System.out.println("user.getUsername() = " + user.getUsername());

        Schedule schedule = userSchedule.getSchedule();
        System.out.println("schedule.getTitle() = " + schedule.getTitle());

    }

    @Test
    @Rollback(value = false)
    @DisplayName("중간테이블 연관관계 테스트")
    void test4() {
        User user = new User();
        user.setUsername("짱구");
        user.setEmail("google.com");

        User user2 = new User();
        user2.setUsername("맹구");
        user2.setEmail("google.com");

        Schedule schedule = new Schedule();
        schedule.setTitle("낮잠");
        schedule.setContents("확인");

        Schedule schedule2 = new Schedule();
        schedule2.setTitle("축구");
        schedule2.setContents("확인");

        List<UserSchedule> userSchedules = new ArrayList<>();
        UserSchedule userSchedule1 = new UserSchedule();
        userSchedule1.setUser(user);
        userSchedule1.setSchedule(schedule);
        userSchedule1.setRole("작성자");
        userSchedules.add(userSchedule1);

        UserSchedule userSchedule2 = new UserSchedule();
        userSchedule2.setUser(user);
        userSchedule2.setSchedule(schedule);
        userSchedule2.setRole("담당자");
        userSchedules.add(userSchedule2);

        UserSchedule userSchedule3 = new UserSchedule();
        userSchedule3.setUser(user2);
        userSchedule3.setSchedule(schedule);
        userSchedule3.setRole("담당자");
        userSchedules.add(userSchedule3);

        UserSchedule userSchedule4 = new UserSchedule();
        userSchedule4.setUser(user2);
        userSchedule4.setSchedule(schedule2);
        userSchedule4.setRole("작성자");
        userSchedules.add(userSchedule4);

        userRepository.save(user);
        userRepository.save(user2);
        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule2);
        userScheduleRepository.saveAll(userSchedules);

    }


}
