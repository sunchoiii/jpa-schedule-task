package com.sparta.jpascheduletask.userschedule;

import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userschedule")
public class UserSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    // 일정 단건 조회 시 담당 유저들의 고유 식별자, 유저명, 이메일  포함 (JPA의 지연 로딩 기능)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String role; //역할 : 작성자, 담당자

}
