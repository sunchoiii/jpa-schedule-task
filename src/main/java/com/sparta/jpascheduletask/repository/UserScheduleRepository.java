package com.sparta.jpascheduletask.repository;

import com.sparta.jpascheduletask.entity.UserSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScheduleRepository extends JpaRepository<UserSchedule, Long> {

}
