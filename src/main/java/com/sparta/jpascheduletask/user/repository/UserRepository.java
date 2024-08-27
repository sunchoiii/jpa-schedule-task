package com.sparta.jpascheduletask.user.repository;

import com.sparta.jpascheduletask.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
