package com.sparta.jpascheduletask.repository;

import com.sparta.jpascheduletask.entity.Schedule;
import com.sparta.jpascheduletask.entity.User;
import com.sparta.jpascheduletask.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

    UserSchedule findByScheduleAndUser(Schedule schedule, User user);
}
