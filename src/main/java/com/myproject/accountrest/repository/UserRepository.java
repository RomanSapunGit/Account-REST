package com.myproject.accountrest.repository;

import com.myproject.accountrest.entity.TaskEntity;
import com.myproject.accountrest.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsername(String username);
    UserEntity getUserByTask(UserEntity user);
    Optional<UserEntity> findByToken(String token);
    UserEntity getByTask(TaskEntity task);

}

