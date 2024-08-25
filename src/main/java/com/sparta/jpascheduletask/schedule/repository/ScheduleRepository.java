package com.sparta.jpascheduletask.schedule.repository;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {

}
