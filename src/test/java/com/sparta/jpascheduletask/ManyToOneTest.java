package com.sparta.jpascheduletask;

import com.sparta.jpascheduletask.comment.entity.Comment;
import com.sparta.jpascheduletask.comment.repository.CommentRepository;
import com.sparta.jpascheduletask.schedule.entity.Schedule;
import com.sparta.jpascheduletask.schedule.repository.ScheduleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ManyToOneTest {

    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 양방향 테스트")
    void test4() {
        Schedule schedule = new Schedule();
        schedule.setUsername("choisun");
        schedule.setTitle("댓글 연관 확인");
        schedule.setContents("할수있어");

        Comment comment = new Comment();
        comment.setUsername("김땡땡");
        comment.setContents("될까");
        comment.setSchedule(schedule); // 외래 키(연관 관계) 설정

        Comment comment2 = new Comment();
        comment2.setUsername("박뭐시기");
        comment2.setContents("되나?");
        comment2.setSchedule(schedule); // 외래 키(연관 관계) 설정

        scheduleRepository.save(schedule);
        commentRepository.save(comment);
        commentRepository.save(comment2);

    }
}

