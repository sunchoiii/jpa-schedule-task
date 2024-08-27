package com.sparta.jpascheduletask.userschedule;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "userschedule")
public class UserSchedule {
    // 일정 N : 유저 M 관계 - 중간테이블 UserSchedule

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role; //역할 : 작성자, 담당자

}
