package com.sparta.jpascheduletask.user.entity;

import com.sparta.jpascheduletask.userschedule.UserSchedule;
import com.sparta.jpascheduletask.user.dto.UserRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class User {
    // 유저명, 이메일, 작성일, 수정일 필드
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;
    private String email;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateDate;

    // 일정 N : 유저 M 관계 - 중간테이블 UserSchedule
    @OneToMany(mappedBy = "user")
    private List<UserSchedule> userSchedules = new ArrayList<>();


    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }

    public void update(UserRequestDto requestDto){
        this.username = requestDto.getUsername();
        this.email = requestDto.getEmail();
    }
}
