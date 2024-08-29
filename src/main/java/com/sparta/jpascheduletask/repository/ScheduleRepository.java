package com.sparta.jpascheduletask.repository;

import com.sparta.jpascheduletask.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
